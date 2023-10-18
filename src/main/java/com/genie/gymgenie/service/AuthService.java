package com.genie.gymgenie.service;

import com.genie.gymgenie.mapper.AuthMapper;
import com.genie.gymgenie.models.JwtToken;
import com.genie.gymgenie.models.Token;
import com.genie.gymgenie.models.User;
import com.genie.gymgenie.models.enums.TokenType;
import com.genie.gymgenie.models.payload.user.login.ForgotPasswordRequest;
import com.genie.gymgenie.models.payload.user.login.LoginRequest;
import com.genie.gymgenie.models.payload.user.login.ResetPasswordRequest;
import com.genie.gymgenie.models.payload.user.registration.RegistrationRequest;
import com.genie.gymgenie.models.payload.user.registration.ResendTokenRequest;
import com.genie.gymgenie.repositories.TokenRepository;
import com.genie.gymgenie.repositories.UserRepository;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.payload.ApiResponse;
import com.genie.gymgenie.utils.EmailSender;
import com.genie.gymgenie.utils.JwtUtils;
import com.nimbusds.jose.util.Pair;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.genie.gymgenie.utils.CurrentRequest.getCurrentRequest;
import static com.genie.gymgenie.utils.ExceptionThrower.authException;
import static com.genie.gymgenie.utils.ExceptionThrower.resourceException;
import static com.genie.gymgenie.utils.TokenValidator.isValidToken;

@Service
@Validated
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;
    private final EmailSender mail;
    private final JwtUtils jwtUtils;
    private final GenieLogger genie = new GenieLogger(AuthService.class);


    public ApiResponse register(@Valid RegistrationRequest request){
        genie.info("Proceeding the registration request for user");
        userExists(request.getEmail(), request.getUsername());

        User user = authMapper.requestToUser(request);
        userRepository.save(user);
        genie.info("User successfully saved to the database");

        Token registrationToken = Token.builder()
                .tokenValue(Token.generateValue())
                .tokenType(TokenType.CONFIRMATION)
                .expires(LocalDateTime.now().plusHours(24))
                .user(user)
                .build();
        tokenRepository.save(registrationToken);
        genie.info("Registration token created and saved to the database");

        mail.send(user.getEmail(), registrationToken.getTokenValue(), "Welcome to GymGenie! Verify your email to get started!");

        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("Your registration was completed successfully. Please confirm your email!")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .build();
    }

    @Transactional
    public ApiResponse confirmRegistration(String token) {
        Token registrationToken = tokenRepository.findByTokenValueAndTokenType(token, TokenType.CONFIRMATION)
                .orElseThrow(() -> {
                    genie.warn("Token not found");
                    throw authException("Token not found. Please ensure you have the correct token or request a new one.", HttpStatus.NOT_FOUND);
                });

        isValidToken(registrationToken);
        tokenRepository.updateConfirmedAt(token, LocalDateTime.now());

        if (registrationToken.getUser().isEnabled()) {
            genie.warn("User already enabled");
            throw authException("The user associated with this token is already enabled", HttpStatus.BAD_REQUEST);
        }

        userRepository.enableAppUser(registrationToken.getUser().getEmail());
        genie.info("User enabled");

        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("Your email was confirmed successfully. You can now login.")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    public ApiResponse resendConfirmation(@Valid ResendTokenRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    genie.warn("User with the provided email not found");
                    throw resourceException("User with the provided email not found. Please ensure you have created an account", HttpStatus.NOT_FOUND);
                });

        if (user.isEnabled()){
            throw authException("Your account is already enabled.You can log in now.", HttpStatus.BAD_REQUEST);
        }

        List<Token> registrationTokens = tokenRepository.findAllByUserEmailAndTokenType(user.getEmail(), TokenType.CONFIRMATION);
        tokenRepository.deleteAll(registrationTokens);

        Token registrationToken = Token.builder()
                .tokenValue(Token.generateValue())
                .tokenType(TokenType.CONFIRMATION)
                .expires(LocalDateTime.now().plusHours(24))
                .user(user)
                .build();
        tokenRepository.save(registrationToken);

        mail.send(user.getEmail(), registrationToken.getTokenValue(), "GymGenie! Verify your email with this new token!");

        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("A new confirmation email has been sent to your email address.")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    public ApiResponse login(@Valid LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    genie.warn("User with the provided username not found");
                    throw resourceException("User with the provided username not found. Please ensure you have entered the correct username", HttpStatus.NOT_FOUND);
                });

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        jwtUtils.revokeAndSaveTokens(user);
        Pair<String, String> jwt = jwtUtils.generateJwtTokens(user);

        return ApiResponse.builder()
                .path(getCurrentRequest())
                .accessToken(jwt.getLeft())
                .refreshToken(jwt.getRight())
                .message("Login successful. Welcome, " + user.getUsername() + "!")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    public ApiResponse sendForgotPasswordEmail(@Valid ForgotPasswordRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    genie.warn("User with the provided email not found");
                    throw resourceException("User with the provided email not found. Please ensure you have created an account", HttpStatus.NOT_FOUND);
                });

        Token passwordToken = Token.builder()
                .tokenValue(Token.generateValue())
                .tokenType(TokenType.PASSWORD)
                .expires(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(passwordToken);

        mail.send(user.getEmail(), passwordToken.getTokenValue(), "GymGenie! Reset your password");
        genie.info("Forgot password email has been sent");

        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("An email has been sent to your registered email address." +
                        " The password reset link will expire in 15 minutes for security reasons.")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    public ApiResponse resetPassword(String token, @Valid ResetPasswordRequest request){
        Token passwordToken = Optional.ofNullable(tokenRepository.findByTokenValueAndTokenType(token, TokenType.PASSWORD))
                .flatMap(byToken -> byToken
                        .filter(reset -> reset.getConfirmed() == null
                                && reset.getExpires().isAfter(LocalDateTime.now())))
                .orElseThrow(() -> {
                            genie.warn("Token not found or is already used!");
                            throw resourceException("Token not found or is already used!", HttpStatus.NOT_FOUND);
                        });

        User user = passwordToken.getUser();
        user.setPassword(request.getPassword());
        userRepository.save(user);

        genie.info("Password changed successfully");
        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("Your password has been successfully reset." +
                        " You can now use your new password to log in.")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    public ApiResponse refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            genie.warn("No authorization token found in the request");
            throw authException("No authorization token found in the request", HttpStatus.UNAUTHORIZED);
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtUtils.extractEmail(refreshToken);

        JwtToken token = null;
        if (userEmail != null) {

            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> {
                        genie.warn("User with the provided email not found");
                        throw resourceException("User with the provided email not found. Please ensure you have created an account", HttpStatus.NOT_FOUND);
                    });

            if (jwtUtils.isValid(refreshToken, user)) {
                genie.debug("Refreshing token for user: {}", user.getEmail());

                String accessToken = jwtUtils.generateToken(user);
                jwtUtils.revokeAndSaveTokens(user);

                token = JwtToken.builder()
                        .user(user)
                        .tokenValue(accessToken)
                        .expired(false)
                        .revoked(false)
                        .build();

                jwtUtils.saveToken(token);
                genie.info("Token refreshed successfully for user");
            } else {
                genie.warn("Invalid refresh token for user");
            }
        }

        if (token == null){
            genie.warn("Refresh token was not created.");
            throw authException("Refresh token was not created. Pleas try again!", HttpStatus.UNAUTHORIZED);
        }

        return ApiResponse.builder()
                .path(getCurrentRequest())
                .accessToken(token.getTokenValue())
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now())
                .build();
    }

    private void userExists (String email, String username) {
        boolean emailExists = userRepository.existsByEmail(email);
        boolean usernameExists = userRepository.existsByUsername(username);

        String message;
        if (emailExists && usernameExists){
            message = "Email and username already exist. Please try different ones.";
        } else if (emailExists) {
            message = "Email already exists. Please try another one.";
        } else if (usernameExists) {
            message = "Username already exists. Please try another one.";
        } else {
            message = "true";
        }

        if(!message.equals("true")){
            throw authException(message, HttpStatus.BAD_REQUEST);
        }
    }

}
