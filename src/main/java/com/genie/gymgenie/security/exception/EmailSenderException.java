package com.genie.gymgenie.security.exception;

import com.genie.gymgenie.security.payload.ApiError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class EmailSenderException extends RuntimeException{
    private final transient ApiError apiError;
    private final transient HttpStatus httpStatus;
}
