package com.genie.gymgenie.security.exception;

import com.genie.gymgenie.security.payload.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidExtractException extends RuntimeException{

    private final transient ApiError apiError;

    public InvalidExtractException(ApiError message) {
        this.apiError = message;
    }
}
