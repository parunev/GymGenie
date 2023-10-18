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
    @Schema(name = "path", description = "The path of the API request", example = "/genie/v1/auth/register", type = "String")
    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(name = "message", description = "Customized user-friendly message", example = "You registered successfully", type = "String")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(name = "accessToken",
            description = "Jwt Access Token",
            example = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJMaW5rZWRFZGdlX0FQSSIsInN1YiI6InBhcnVuZXZAZ21haWwuY29tIiwiZXhwIjoxNjk2MzM0MTgyLCJpYXQiOjE2OTYzMzM1ODIsImp0aSI6IjRmYjI1ODAxLWI1NGEtNDhhNi1iYjcwLTVlMGRkZGUxYTdiZiIsInNjb3BlIjoiUk9MRV9VU0VSIn0.pTSULuQ4SXb1sFYCpe4dEVzMYo_h7XFXFa8NaNcqazGP9bIljsx9WdRUWQQOXrcWP7mQItiu_CMHjA46d9El2Ai5dnpYAHUYtebppclkuaJntrJzFK3aSqIhVmBqKxowVgTZH4783KqfJ47TtGIWBfywaHNh0rh9A-A2DXDh5_hRoKvQY9M8v0UNbD3cLca6sUH80y-WLR9KjZaPbwwYQHIZxduBi8HHYgyAG4zCDx9eazJc8_t6lGQlz-JZJnzIaorcavRx_lA3q9Ablus05Ljj7Dy_NaGC0F83m-nP5wVY2fVGfrc2H5N1MwNkngM_ji7poc9qhKGBc3b_x-_KAw",
            type = "String")
    private String accessToken;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(name = "refreshToken",
            description = "Jwt Refresh Token",
            example = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJMaW5rZWRFZGdlX0FQSSIsInN1YiI6InBhcnVuZXZAZ21haWwuY29tIiwiZXhwIjoxNjk2MzM0MTgyLCJpYXQiOjE2OTYzMzM1ODIsImp0aSI6IjRmYjI1ODAxLWI1NGEtNDhhNi1iYjcwLTVlMGRkZGUxYTdiZiIsInNjb3BlIjoiUk9MRV9VU0VSIn0.pTSULuQ4SXb1sFYCpe4dEVzMYo_h7XFXFa8NaNcqazGP9bIljsx9WdRUWQQOXrcWP7mQItiu_CMHjA46d9El2Ai5dnpYAHUYtebppclkuaJntrJzFK3aSqIhVmBqKxowVgTZH4783KqfJ47TtGIWBfywaHNh0rh9A-A2DXDh5_hRoKvQY9M8v0UNbD3cLca6sUH80y-WLR9KjZaPbwwYQHIZxduBi8HHYgyAG4zCDx9eazJc8_t6lGQlz-JZJnzIaorcavRx_lA3q9Ablus05Ljj7Dy_NaGC0F83m-nP5wVY2fVGfrc2H5N1MwNkngM_ji7poc9qhKGBc3b_x-_KAw",
            type = "String")
    private String refreshToken;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(name = "status", description = "The HTTP status code of the response", example = "200", type = "HttpStatus")
    private HttpStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(name = "timestamp", description = "Timestamp of the API response", example = "2023-10-18T18:05:29.290134", type = "LocalDateTime")
    private LocalDateTime timestamp;
}
