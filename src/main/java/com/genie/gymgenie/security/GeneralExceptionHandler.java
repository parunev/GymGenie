package com.genie.gymgenie.security;

import com.genie.gymgenie.security.exception.AuthServiceException;
import com.genie.gymgenie.security.exception.EmailSenderException;
import com.genie.gymgenie.security.exception.InvalidExtractException;
import com.genie.gymgenie.security.exception.ResourceNotFoundException;
import com.genie.gymgenie.security.payload.ApiError;
import dev.ai4j.openai4j.OpenAiHttpException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.genie.gymgenie.utils.CurrentRequest.getCurrentRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class GeneralExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ApiError> handleException
            (Exception e, HttpServletRequest request) {
        return new ResponseEntity<>(ApiError.builder()
                .path(request.getRequestURI())
                .error(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidExtractException.class)
    public ResponseEntity<ApiError> handleInvalidExtractException(InvalidExtractException ex){
        return new ResponseEntity<>(ex.getApiError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OpenAiHttpException.class)
    public ResponseEntity<ApiError> handleOpenAiHttpException(OpenAiHttpException ex){
        return new ResponseEntity<>(ApiError.builder()
                .path(getCurrentRequest())
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<String> handleHttpStatusCodeException(HttpStatusCodeException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<>(ex.getApiError(), ex.getHttpStatus());
    }

    @ExceptionHandler(AuthServiceException.class)
    public ResponseEntity<ApiError> handleAuthServiceException(AuthServiceException ex){
        return new ResponseEntity<>(ex.getApiError(), ex.getHttpStatus());
    }

    @ExceptionHandler(EmailSenderException.class)
    public ResponseEntity<ApiError> handleEmailSenderException(EmailSenderException ex){
        return new ResponseEntity<>(ex.getApiError(), ex.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleValidationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(error -> {
            String errorMessage = error.getMessage();
            errors.put("VIOLATED CONSTRAINT", errorMessage);
        });

        return new ResponseEntity<>(ApiError.builder()
                .path(getCurrentRequest())
                .error("Validation failed. One or more constraints were violated.")
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
