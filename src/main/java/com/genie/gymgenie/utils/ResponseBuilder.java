package com.genie.gymgenie.utils;

import com.genie.gymgenie.security.payload.ApiResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static com.genie.gymgenie.utils.CurrentRequest.getCurrentRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseBuilder {

    public static ApiResponse response(String message) {
        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message(message)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    public static ApiResponse responseLogin(String accessToken, String refreshToken, String message) {
        return ApiResponse.builder()
                .path(getCurrentRequest())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message(message)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }
}
