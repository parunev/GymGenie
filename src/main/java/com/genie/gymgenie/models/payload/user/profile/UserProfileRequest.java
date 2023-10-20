package com.genie.gymgenie.models.payload.user.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genie.gymgenie.models.enums.user.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Request payload for creating the user profile", description = "Create information about the user")
public class UserProfileRequest {

    @NotNull(message = "Please tell us how old are you")
    @Min(value = 0, message = "Age must be a positive number")
    @JsonProperty("age")
    @Schema(description = "Age of the user.", example = "23", type = "Integer")
    private Integer userAge;

    @NotNull(message = "Please provide your weight")
    @Positive(message = "Weight must be a positive number")
    @JsonProperty("weight")
    @Schema(description = "Weight of the user.", example = "90.5", type = "Double")
    private Double userWeight;

    @NotNull(message = "Please provide your height")
    @Positive(message = "Height must be a positive number")
    @JsonProperty("height")
    @Schema(description = "Height of the user.", example = "186", type = "Double")
    private Double userHeight;

    @NotNull(message = "Please tell us your gender or simply choose 'Other'")
    @JsonProperty("gender")
    @Schema(description = "Gender of the user.", example = "Woman", enumAsRef = true, type = "ENUM")
    private Gender userGender;

    @NotNull(message = "Please tell us how many times you workout per week")
    @Min(value = 0, message = "Exercise per week must be a non-negative number")
    @JsonProperty("exercisePerWeek")
    @Schema(description = "Number of exercises per week the user performs.", example = "3", type = "Integer")
    private Integer userExercisePerWeek;

    @NotNull(message = "Please tell us how much time you spend per workout session")
    @Min(value = 0, message = "Average workout duration must be a non-negative number")
    @JsonProperty("avgWorkoutDuration")
    @Schema(description = "Average duration of the user's workouts.", example = "90", type = "Integer")
    private Integer userAvgWorkoutDuration;

    @NotNull(message = "Please tell us are you open for dietary change")
    @JsonProperty("openToDietaryChange")
    @Schema(description = "Indicates if the user is open to dietary changes.", example = "true", type = "Boolean")
    private Boolean userOpenToDietaryChange;

    @NotNull(message = "Please tell us if you skip meals")
    @JsonProperty("skipMeals")
    @Schema(description = "Indicates if the user skips meals.", example = "false", type = "Boolean")
    private Boolean userSkipMeals;

    @NotNull(message = "Please tell us are you open for buying equipment")
    @JsonProperty("openToPurchase")
    @Schema(description = "Indicates if the user is open to purchasing recommendations.", example = "true", type = "Boolean")
    private Boolean userOpenToPurchase;

    @NotNull(message = "Please tell us how many meals you eat per day")
    @Min(value = 0, message = "Meals per day must be a non-negative number")
    @JsonProperty("mealPerDay")
    @Schema(description = "Number of meals the user consumes per day.", example = "5", type = "Integer")
    private Integer userMealsPerDay;

    @JsonProperty("description")
    @Schema(description = "Additional user profile description.")
    private UserProfileDescriptionRequest userProfileDescription;

    @Schema(description = "List of injuries the user has experienced.")
    private List<InjuryRequest> injuries;

    @Schema(description = "List of medical histories for the user.")
    private List<MedicalHistoryRequest> medicalHistories;
}
