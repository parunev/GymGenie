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
        summary = "Request a new jwt access token with the refresh one provided earlier.",
        description = "POST Endpoint to request a new JWT Access token via the refresh one.")
@ApiResponse(
        responseCode = "200"
        , description = "Access Token provided"
        , content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.genie.gymgenie.security.payload.ApiResponse.class))})
@ApiResponse(
        responseCode = "404",
        description = "User with the provided username not found. Please ensure you have entered the correct username",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
@ApiResponse(
        responseCode = "500",
        description = "Invalid or Expired JWT Token",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})

public @interface ApiRefreshToken {
}
