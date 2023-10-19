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
        summary = "Reset the password in the database",
        description = "POST endpoint to change the password")
@ApiResponse(
        responseCode = "200",
        description = "Your password has been successfully reset." +
                " You can now use your new password to log in.",
        content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.genie.gymgenie.security.payload.ApiResponse.class))
        })
@ApiResponse(
        responseCode = "404",
        description = "Token not found or is already used! There is possibility to get constraint error too",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
@ApiResponse(
        responseCode = "429",
        description = "Too many requests. Please try to reset your password later.",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}
)
public @interface ApiResetPassword {
}
