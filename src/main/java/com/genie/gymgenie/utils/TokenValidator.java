package com.genie.gymgenie.utils;

import com.genie.gymgenie.models.Token;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.exception.AuthServiceException;
import com.genie.gymgenie.security.payload.ApiError;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static com.genie.gymgenie.utils.CurrentRequest.getCurrentRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenValidator {
    private static final GenieLogger genie = new GenieLogger(TokenValidator.class);

    public static void isValidToken(Token token) {
        if (token.getConfirmed() != null && token.getExpires().isBefore(LocalDateTime.now())) {
            genie.warn("Token already confirmed");
            throw new AuthServiceException(ApiError.builder()
                    .path(getCurrentRequest())
                    .error("The provided token has already been confirmed or has expired. Request new one")
                    .timestamp(LocalDateTime.now())
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }
}
