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
        description = "GET endpoint to validate the user's email when they verify through the email confirmation.",
        summary = "Confirm user registration via email")
@ApiResponse(
        responseCode = "200"
        , description = "User-friendly response will be presented stating that 'Your email was confirmed successfully. You can now login.'"
        , content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.genie.gymgenie.security.payload.ApiResponse.class))}
)
@ApiResponse(
        responseCode = "404"
        , description = "May occur if the provided in the request token doesn't exists in the database. Again a user-friendly response" +
        " will be presented stating the following 'Token not found. Please ensure you have the correct token or request a new one.'"
        , content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}
)
@ApiResponse(
        responseCode = "400"
        , description = """
        May occur during the validation process.
         Either the token has expired, has been confirmed, or it's overall invalid.
         Possible responses are:
        1. The provided token has already been confirmed.
        2. The provided token has expired. Please request a new one
        3. The user associated with this token is already enabled"""
        ,content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}
)
public @interface ApiRegistrationConfirm {
}
