package com.genie.gymgenie.utils.openapi;

import com.genie.gymgenie.security.payload.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Reset forgotten password, by receiving an email",
        description = "POST endpoint to send an email to the user with a one-time password reset url")
@ApiResponse(
        responseCode = "200",
        description = "An email has been sent to your registered email address. " +
                "The password reset link will expire in 24 hours for security reasons.",
        content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.genie.gymgenie.security.payload.ApiResponse.class))
        })
@ApiResponse(
        responseCode = "404",
        description = "User with the provided username not found. Please ensure you have entered the correct username",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
@ApiResponse(
        responseCode = "500",
        description = "Failed to send email",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
@ApiResponse(
        responseCode = "429",
        description = "Too many requests. Please try again later(5 requests/minute)",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
public @interface ApiForgotPassword {
}
