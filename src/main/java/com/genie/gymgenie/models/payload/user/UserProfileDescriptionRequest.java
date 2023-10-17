package com.genie.gymgenie.models.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genie.gymgenie.models.enums.user.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Request payload for user profile description", description = "Additional information about the user")
public class UserProfileDescriptionRequest {

    @JsonProperty("fitnessLevel")
    @NotNull(message = "Please provide your current fitness level.")
    @Schema(description = "The fitness level of the user.",example = "ADVANCED", enumAsRef = true, type = "ENUM")
    private FitnessLevel userFitnessLevel;

    @JsonProperty("workoutLocation")
    @NotNull(message = "Please specify your favourite workout location.")
    @Schema(description = "The workout location preference of the user.", example = "GYM", enumAsRef = true, type = "ENUM")
    private WorkoutLocation userWorkoutLocation;

    @JsonProperty("exerciseType")
    @NotNull(message = "Please select what exercise types suits you best.")
    @Schema(description = "The preferred exercise type of the user.", example = "STRENGTH_TRAINING", enumAsRef = true, type = "ENUM")
    private ExerciseType userExerciseType;

    @JsonProperty("stressLevel")
    @NotNull(message = "Please indicate your stress level. We know it's hard...")
    @Schema(description = "The stress level of the user.", example = "MODERATE", enumAsRef = true, type = "ENUM")
    private StressLevel userStressLevel;

    @JsonProperty("jobType")
    @NotNull(message = "Please specify the type of job you're currently positioned on.")
    @Schema(description = "The type of job the user has.", example = "SEDENTARY", enumAsRef = true, type = "ENUM")
    private JobType userJobType;

    @JsonProperty("energyLevel")
    @NotNull(message = "Please tell us how energized are you. Maybe HIGH?")
    @Schema(description = "The energy level of the user.", example = "HIGH", enumAsRef = true, type = "ENUM")
    private EnergyLevels userEnergyLevel;

    @JsonProperty("sleepQuality")
    @NotNull(message = "Please tell us your quality of sleep.")
    @Schema(description = "The quality of sleep the user gets.", example = "EXCELLENT", enumAsRef = true, type = "ENUM")
    private SleepQuality userSleepQuality;

    @JsonProperty("shortTermGoals")
    @Schema(description = "A list of short-term goals of the user.", example = "Lose weight, Build Muscle", type = "List<String>")
    private List<@NotBlank(message = "Provide at least one short term goal") String> userShortTermGoals;

    @JsonProperty("longTermGoals")
    @Schema(description = "A list of long-term goals of the user.", example = "Maintain a healthy lifestyle, Run a marathon", type = "List<String>")
    private List<@NotBlank(message = "Provide at least one long term goal") String> userLongTermGoals;

    @JsonProperty("dietaryRestrictions")
    @Schema(description = "A list of dietary restrictions of the user.", example = "Gluten-free, Vegetarian, Keto", type = "List<String>")
    private List<String> userDietaryRestrictions;

    @JsonProperty("medicalConditions")
    @Schema(description = "A list of medical conditions the user has.", example = "Allergic to peanuts, Asthma", type = "List<String>")
    private List<String> userMedicalConditions;

    @JsonProperty("availableEquipment")
    @Schema(description = "A list of available exercise equipment for the user.", example = "Dumbbells 5kg, Treadmill, Gym equipment", type = "List<String>")
    private List<@NotBlank(message = "Provide what type of equipment you can use") String> userAvailableEquipment;

    @JsonProperty("dailySchedule")
    @Schema(description = "A list describing the user's daily schedule.", example = "Morning job, At the job, With family, With friends", type = "List<String>")
    private List<@NotBlank(message = "Give us an example of your daily schedules") String> userDailySchedule;

    @JsonProperty("dailyDiet")
    @Schema(description = "A list describing the user's daily diet.", example = "Oatmeal, Salad, T-bone steak", type = "List<String>")
    private List<@NotBlank(message = "Give us an example of your daily meals") String> userDailyDiet;
}
