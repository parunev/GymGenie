package com.genie.gymgenie.service;

import com.genie.gymgenie.mapper.AuthMapper;
import com.genie.gymgenie.models.Token;
import com.genie.gymgenie.models.User;
import com.genie.gymgenie.models.enums.TokenType;
import com.genie.gymgenie.models.payload.user.registration.RegistrationRequest;
import com.genie.gymgenie.models.payload.user.registration.ResendTokenRequest;
import com.genie.gymgenie.repositories.TokenRepository;
import com.genie.gymgenie.repositories.UserRepository;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.payload.ApiResponse;
import com.genie.gymgenie.utils.EmailSender;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

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
    private final AuthMapper authMapper;
    private final EmailSender mail;
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
