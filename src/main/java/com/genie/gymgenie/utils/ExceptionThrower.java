package com.genie.gymgenie.utils;

import com.genie.gymgenie.security.exception.AuthServiceException;
import com.genie.gymgenie.security.exception.ResourceNotFoundException;
import com.genie.gymgenie.security.payload.ApiError;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static com.genie.gymgenie.utils.CurrentRequest.getCurrentRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionThrower {

    public static AuthServiceException authException(String message, HttpStatus status) {
        throw new AuthServiceException(ApiError.builder()
                .path(getCurrentRequest())
                .error(message)
                .timestamp(LocalDateTime.now())
                .build(), status);
    }

    public static ResourceNotFoundException resourceException(String message, HttpStatus status) {
        throw new ResourceNotFoundException(ApiError.builder()
                .path(getCurrentRequest())
                .error(message)
                .timestamp(LocalDateTime.now())
                .build(), status);
    }

}
