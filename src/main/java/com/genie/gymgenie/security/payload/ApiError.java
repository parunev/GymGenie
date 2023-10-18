package com.genie.gymgenie.security.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@Schema(name = "Default Api Error Response")
public class ApiError {

    @Schema(name = "The path where the error occurred", example = "/genie/v1/auth/register", type = "String")
    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(name = "Custom error message specifying the error", example = "Your not enabled user. You cannot enter!", type = "String")
    private String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(name = "Custom field-level error details", example = "CONSTRAINT VIOLATION: 1", type = "Map<String, String>")
    private Map<String, String> errors;

    @Schema(name = "Timestamp of the error", example = "2023-10-18T18:05:29.290134", type = "LocalDateTime")
    private LocalDateTime timestamp;
}
