package com.genie.gymgenie.security.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(name = "Default Api Response")
public class ApiResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(name = "The path of the API request", example = "/genie/v1/auth/register", type = "String")
    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(name = "Customized user-friendly message", example = "You registered successfully", type = "String")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(name = "The HTTP status code of the response", example = "200")
    private HttpStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(name = "Timestamp of the API response", example = "2023-10-18T18:05:29.290134")
    private LocalDateTime timestamp;
}
