package com.genie.gymgenie.models.payload.user.profile.updatewrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genie.gymgenie.models.enums.user.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ProfileDescriptionUpdateRequest {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("fitnessLevel")
    @Schema(description = "The fitness level of the user.",example = "ADVANCED", enumAsRef = true, type = "ENUM")
    private FitnessLevel userFitnessLevel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("workoutLocation")
    @Schema(description = "The workout location preference of the user.", example = "GYM", enumAsRef = true, type = "ENUM")
    private WorkoutLocation userWorkoutLocation;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("exerciseType")
    @Schema(description = "The preferred exercise type of the user.", example = "STRENGTH_TRAINING", enumAsRef = true, type = "ENUM")
    private ExerciseType userExerciseType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("stressLevel")
    @Schema(description = "The stress level of the user.", example = "MODERATE", enumAsRef = true, type = "ENUM")
    private StressLevel userStressLevel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("jobType")
    @Schema(description = "The type of job the user has.", example = "SEDENTARY", enumAsRef = true, type = "ENUM")
    private JobType userJobType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("energyLevel")
    @Schema(description = "The energy level of the user.", example = "HIGH", enumAsRef = true, type = "ENUM")
    private EnergyLevels userEnergyLevel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("sleepQuality")
    @Schema(description = "The quality of sleep the user gets.", example = "EXCELLENT", enumAsRef = true, type = "ENUM")
    private SleepQuality userSleepQuality;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("shortTermGoals")
    @Schema(description = "A list of short-term goals of the user.", example = "Lose weight, Build Muscle", type = "List<String>")
    private List<String> userShortTermGoals;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("longTermGoals")
    @Schema(description = "A list of long-term goals of the user.", example = "Maintain a healthy lifestyle, Run a marathon", type = "List<String>")
    private List<String> userLongTermGoals;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dietaryRestrictions")
    @Schema(description = "A list of dietary restrictions of the user.", example = "Gluten-free, Vegetarian, Keto", type = "List<String>")
    private List<String> userDietaryRestrictions;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("medicalConditions")
    @Schema(description = "A list of medical conditions the user has.", example = "Allergic to peanuts, Asthma", type = "List<String>")
    private List<String> userMedicalConditions;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("availableEquipment")
    @Schema(description = "A list of available exercise equipment for the user.", example = "Dumbbells 5kg, Treadmill, Gym equipment", type = "List<String>")
    private List<String> userAvailableEquipment;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dailySchedule")
    @Schema(description = "A list describing the user's daily schedule.", example = "Morning job, At the job, With family, With friends", type = "List<String>")
    private List<String> userDailySchedule;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dailyDiet")
    @Schema(description = "A list describing the user's daily diet.", example = "Oatmeal, Salad, T-bone steak", type = "List<String>")
    private List<String> userDailyDiet;
}
