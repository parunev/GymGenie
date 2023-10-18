package com.genie.gymgenie.models.payload.user.registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genie.gymgenie.utils.password.PasswordConfirmation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordConfirmation
@Schema(name = "Request payload for user registration", description = "Uses custom annotation @PasswordConfirmation" +
        " to check if the password field matches the confirmPassword field")
public class RegistrationRequest {

    @NotBlank(message = "Please enter your username")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{6,29}$",
            message = "Your username must start with alphabet letter, must be between 7 and 30 symbols. Only underscore allowed!")
    @Schema(name = "username",description = "User's username",example = "genie", type = "String")
    private String username;

    @NotBlank(message = "Please enter your email address.")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Please enter a valid email address.")
    @Schema(name = "email", description = "User's email address", example = "genie@gmail.com", type = "String")
    private String email;

    @NotBlank(message = "Please enter your first name.")
    @Size(max = 50, message = "First name must be less than 50 characters long.")
    @Pattern(regexp = "^[a-zA-Zà-üÀ-Ü]+$", message = "First name should contain only letters")
    @Schema(name="firstName", description = "User's first name", example = "Martin", type = "String")
    private String firstName;

    @NotBlank(message = "Please enter your last name.")
    @Size(max = 50, message = "Last name must be less than 50 characters long.")
    @Pattern(regexp = "^[a-zA-Zà-üÀ-Ü]+$", message = "Last name should contain only letters")
    @Schema(name = "lastName", description = "User's last name", example = "Parunev", type = "String")
    private String lastName;

    @NotBlank(message = "Please enter a password.")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.,#\"':;><\\\\/|\\[\\]€£~{}()+=^_-])[A-Za-z\\d@$!%*?&.,#\"':;><\\\\/|\\[\\]€£~{}()+=^_-]{7,}$",
            message = "Your password must have at least 8 characters, with a mix of uppercase, lowercase, numbers and symbols.")
    @Schema(name = "password", description = "User's password. The password will be encoded with BCrypt", example = "Genie123!@", type = "String")
    private String password;

    @NotBlank(message = "Please confirm your password.")
    @JsonProperty("confirm")
    @Schema(name = "confirm", description =  "Confirmation of the password. The confirm password needs to match with the password", example = "Genie123!@", type = "String")
    private String confirmPassword;
}
