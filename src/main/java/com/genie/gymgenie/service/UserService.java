package com.genie.gymgenie.service;

import com.genie.gymgenie.models.Token;
import com.genie.gymgenie.models.User;
import com.genie.gymgenie.models.enums.TokenType;
import com.genie.gymgenie.models.payload.user.profile.ChangeEmailRequest;
import com.genie.gymgenie.models.payload.user.profile.ChangePasswordRequest;
import com.genie.gymgenie.repositories.TokenRepository;
import com.genie.gymgenie.repositories.UserRepository;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.payload.ApiResponse;
import com.genie.gymgenie.utils.JwtUtils;
import com.genie.gymgenie.utils.PasswordUtils;
import com.genie.gymgenie.utils.email.EmailPattern;
import com.genie.gymgenie.utils.email.EmailSender;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

import static com.genie.gymgenie.security.CurrentUser.getCurrentUserDetails;
import static com.genie.gymgenie.utils.CurrentRequest.getCurrentRequest;
import static com.genie.gymgenie.utils.ExceptionThrower.authException;
import static com.genie.gymgenie.utils.ExceptionThrower.resourceException;
import static com.genie.gymgenie.utils.TokenValidator.isValidToken;
import static com.genie.gymgenie.utils.email.PasswordPatterns.PROFILE_PASSWORD_CHANGE;

@Service
@Validated
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordUtils passwordUtils;
    private final JwtUtils jwtUtils;
    private final EmailSender mail;
    private final GenieLogger genie = new GenieLogger(UserService.class);

    // needs to be redirected to the front-end
    private static final String CONFIRMATION_LINK = "http://localhost:8080/genie/v1/user/change-email/confirm?token=%s&e=%s";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        genie.info("Attempt to load a user by username: {}", username);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", username);
                    throw new UsernameNotFoundException(
                            "User with username " + username + " not found!"
                    );
                });
    }

    public ApiResponse changeUserPassword(@Valid ChangePasswordRequest request){
        User user = userRepository.findByUsername(getCurrentUserDetails().getUsername())
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", getCurrentUserDetails().getUsername());
                    throw resourceException("No account associated with this username(%s) found.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        boolean validPassword = passwordUtils.isThePasswordValid(
                request.getOldPassword(), user.getPassword(), request.getNewPassword(), request.getConfirmNewPassword());

        if (validPassword){
            genie.info("Password validations passed. User password will be changed");
            user.setPassword(passwordUtils.encodePassword(request.getNewPassword()));
            userRepository.save(user);
        }

        mail.send(user.getEmail(), PROFILE_PASSWORD_CHANGE, "GymGenie! Your password has been changed!");
        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("Your password has been changed successfully!")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    public ApiResponse changeUserEmail(@Valid ChangeEmailRequest request){
        User user = userRepository.findByUsername(getCurrentUserDetails().getUsername())
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", getCurrentUserDetails().getUsername());
                    throw resourceException("No account associated with this username(%s) found.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        if (user.getEmail().equals(request.getNewEmail())){
            genie.warn("User with username {} tried to change the email to the same email", getCurrentUserDetails().getUsername());
            throw authException("Dear %s, you have tried to change the email to the same email."
                    .formatted(getCurrentUserDetails().getUsername()), HttpStatus.BAD_REQUEST);
        } else if (userRepository.existsByEmail(request.getNewEmail())){
            genie.warn("User with username {} tried to change the email to an existing email", getCurrentUserDetails().getUsername());
            throw authException("Dear %s, you have tried to change the email to an existing email."
                    .formatted(getCurrentUserDetails().getUsername()), HttpStatus.BAD_REQUEST);
        }

        if (!passwordUtils.samePassword(request.getUserPassword(), user.getPassword())){
            genie.info("User password is not the same as the current one. User email will not be changed");
            throw authException("Dear %s, you have tried to change the email with an invalid password."
                    .formatted(getCurrentUserDetails().getUsername()), HttpStatus.BAD_REQUEST);
        }

        Token token = Token.builder()
                .user(user)
                .tokenValue(Token.generateValue())
                .tokenType(TokenType.CONFIRMATION)
                .expires(LocalDateTime.now().plusMinutes(30))
                .build();
        tokenRepository.save(token);

        String encodedEmail = enDecEmail(request.getNewEmail(), "encode");
        mail.send(user.getEmail(), EmailPattern.changeEmail(CONFIRMATION_LINK.formatted(token.getTokenValue(),encodedEmail)),"GymGenie! Confirm your email change!");

        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("Please check your email to confirm the change.")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    @Transactional
    public ApiResponse verifyChangeUserEmail(String token, String email){
        Token confirmationToken = tokenRepository.findByTokenValueAndTokenType(token, TokenType.CONFIRMATION)
                .orElseThrow(() -> {
                    genie.warn("Token not found");
                    throw authException("Token not found. Please ensure you have the correct token or request a new one.", HttpStatus.NOT_FOUND);
                });

        isValidToken(confirmationToken);

        User user = confirmationToken.getUser();
        tokenRepository.updateConfirmedAt(token, LocalDateTime.now());

        String decodedEmail = enDecEmail(email, "decode");
        user.setEmail(decodedEmail);
        userRepository.save(user);

        jwtUtils.deleteUserJwtTokens(user);
        genie.info("The email has been changed successfully!");
        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("The email has been changed successfully! Please, log in again!")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    private String enDecEmail(String email, String operation) {
        byte[] bytes;
        if(operation.equals("encode")){
            bytes = email.getBytes(StandardCharsets.UTF_8);
            return Base64.getEncoder().encodeToString(bytes);
        } else {
            bytes = Base64.getDecoder().decode(email);
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }
}
