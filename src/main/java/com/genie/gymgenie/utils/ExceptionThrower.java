package com.genie.gymgenie.utils;

import com.genie.gymgenie.security.exception.AuthServiceException;
import com.genie.gymgenie.security.exception.InvalidExtractException;
import com.genie.gymgenie.security.exception.ResourceNotFoundException;
import com.genie.gymgenie.security.payload.ApiError;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static com.genie.gymgenie.utils.CurrentRequest.getCurrentRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionThrower {

    public static final String TOO_MANY_REQUESTS = "Too many requests. Please try %s later.";
    public static final String TOO_MANY_REQUEST_MSG = "Dear %s, you have exceeded the number of requests allowed per minute. Please try again later.";

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

    public static InvalidExtractException extractException(String message){
        throw new InvalidExtractException(ApiError.builder()
                .path(getCurrentRequest())
                .error(message)
                .timestamp(LocalDateTime.now())
                .build());
    }

}
