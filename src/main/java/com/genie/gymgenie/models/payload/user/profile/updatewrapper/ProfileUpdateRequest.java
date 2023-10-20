package com.genie.gymgenie.models.payload.user.profile.updatewrapper;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(name = "Request payload for updating the user profile description", description = "Update the information about the user")
public class ProfileUpdateRequest {

    @Min(value = 0, message = "Age must be a positive number")
    @JsonProperty("age")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Age of the user.", example = "23", type = "Integer")
    private Integer userAge;

    @Positive(message = "Weight must be a positive number")
    @JsonProperty("weight")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Weight of the user.", example = "90.5", type = "Double")
    private Double userWeight;

    @Positive(message = "Height must be a positive number")
    @JsonProperty("height")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Height of the user.", example = "186", type = "Double")
    private Double userHeight;

    @Min(value = 0, message = "Exercise per week must be a non-negative number")
    @JsonProperty("exercisePerWeek")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Number of exercises per week the user performs.", example = "3", type = "Integer")
    private Integer userExercisePerWeek;

    @Min(value = 0, message = "Average workout duration must be a non-negative number")
    @JsonProperty("avgWorkoutDuration")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Average duration of the user's workouts.", example = "90", type = "Integer")
    private Integer userAvgWorkoutDuration;

    @JsonProperty("openToDietaryChange")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Indicates if the user is open to dietary changes.", example = "true", type = "Boolean")
    private Boolean userOpenToDietaryChange;

    @JsonProperty("skipMeals")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Indicates if the user skips meals.", example = "false", type = "Boolean")
    private Boolean userSkipMeals;

    @JsonProperty("openToPurchase")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Indicates if the user is open to purchasing recommendations.", example = "true", type = "Boolean")
    private Boolean userOpenToPurchase;

    @Min(value = 0, message = "Meals per day must be a non-negative number")
    @JsonProperty("mealPerDay")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Number of meals the user consumes per day.", example = "5", type = "Integer")
    private Integer userMealsPerDay;
}
