package com.genie.gymgenie.models.payload.user.registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genie.gymgenie.models.enums.user.*;
import com.genie.gymgenie.utils.password.PasswordConfirmation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
@PasswordConfirmation
@Schema(name = "Request payload for user registration", description = "Uses custom annotation @PasswordConfirmation" +
        " to check if the password field matches the confirmPassword field")
public class RegistrationRequest {

    @NotNull(message = "We just need to know your gender to help you on your fitness journey")
    @Schema(description = "Gender of the user", example = "MALE")
    private Gender gender;

    @NotNull(message = "Hold on, champ! You need to select at least one fitness goal. Whether it's getting stronger, faster, or just healthier, pick one to get started!")
    @Size(min = 1, message = "At least one goal must be selected")
    @Schema(description = "List of user's fitness goals")
    private List<Goal> goals;

    @NotNull(message = "You're unstoppable, but we need to know what keeps you going!")
    @Size(min = 1, message = "At least one motivation must be selected")
    @Schema(description = "List of user's motivations")
    private List<Motivation> motivations;

    @NotNull(message = "Let's find your starting point! Select your fitness level.")
    @Schema(description = "Fitness level of the user", example = "BEGINNER")
    private FitnessLevel fitnessLevel;

    @NotNull(message = "Pick one that best describes your daily hustle. Let's get moving!")
    @Schema(description = "Activity level of the user", example = "MODERATE")
    private ActivityLevel activityLevel;

    @NotNull(message = "We need to know your height to help you on your fitness journey")
    @Min(122)
    @Max(213)
    @Schema(description = "User's height in centimeters", example = "175")
    private Integer height;

    @NotNull(message = "We need to know your weight to help you on your fitness journey")
    @Min(value = 36)
    @Max(180)
    @Schema(description = "User's weight in kgs", example = "75")
    private Integer weight;

    @NotNull(message = "We need to know your target weight to help you on your fitness journey")
    @Min(value = 36)
    @Max(180)
    @Schema(description = "User's target weight in kilograms", example = "70")
    private Integer targetWeight;

    @Size(min = 1, message = "At least one workout area must be selected")
    @Schema(description = "List of health issues the user may have")
    private List<HealthIssues> healthIssues;

    @Size(min = 1, message = "Prioritizing health! Please select at least one health issue that applies to you," +
            " or choose 'NONE OF THE ABOVE' if none of them are relevant. Whether it's allergies, injuries, or just a deep love for broccoli," +
            " let us know!")
    @Schema(description = "List of workout equipment the user has access to")
    private List<Equipment> equipment;

    @NotNull(message = "Please choose your preferred workout frequency, whether it's 'DAILY,' 'ONCE A WEEK,' or anything in between.")
    @Schema(description = "User's preferred workout frequency", example = "THREE_TIMES_A_WEEK")
    private WorkoutFrequency workoutFrequency;

    @NotNull(message = "Please select at least one preferred workout day to kickstart your fitness journey.")
    @Size(min = 1, message = "At least one workout day must be selected")
    @Schema(description = "List of user's preferred workout days")
    private List<WorkoutDays> workoutDays;

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

    @NotNull(message = "Please enter your age.")
    @Min(14)
    @Max(100)
    @Schema(name = "age", description = "User's age", example = "25", type = "Integer")
    private Integer age;

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
