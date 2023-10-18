package com.genie.gymgenie.models.payload.user.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Request payload for resetting a forgotten password")
public class ForgotPasswordRequest {

    @NotBlank(message = "Please enter your email address.")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Please enter a valid email address.")
    @Schema(name = "email", description = "User's email", example = "genie@gmail.com", type = "String")
    private String email;
}
