package com.genie.gymgenie.security.payload;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {
    private String path;
    private String error;
    private LocalDateTime timestamp;
}
