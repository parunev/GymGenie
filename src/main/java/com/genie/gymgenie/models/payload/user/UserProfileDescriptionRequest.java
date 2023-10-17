package com.genie.gymgenie.models.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genie.gymgenie.models.enums.user.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDescriptionRequest {

    @JsonProperty("fitnessLevel")
    @NotNull(message = "Fitness level is required")
    private FitnessLevel userFitnessLevel;

    @JsonProperty("workoutLocation")
    @NotNull(message = "Workout location is required")
    private WorkoutLocation userWorkoutLocation;

    @JsonProperty("exerciseType")
    @NotNull(message = "Exercise type is required")
    private ExerciseType userExerciseType;

    @JsonProperty("stressLevel")
    @NotNull(message = "Stress level is required")
    private StressLevel userStressLevel;

    @JsonProperty("jobType")
    @NotNull(message = "Job type is required")
    private JobType userJobType;

    @JsonProperty("energyLevel")
    @NotNull(message = "Energy level is required")
    private EnergyLevels userEnergyLevel;

    @JsonProperty("sleepQuality")
    @NotNull(message = "Sleep quality is required")
    private SleepQuality userSleepQuality;

    @JsonProperty("shortTermGoals")
    private List<@NotBlank(message = "") String> userShortTermGoals;

    @JsonProperty("longTermGoals")
    private List<@NotBlank(message = "") String> userLongTermGoals;

    @JsonProperty("dietaryRestrictions")
    private List<@NotBlank(message = "") String> userDietaryRestrictions;

    @JsonProperty("medicalConditions")
    private List<@NotBlank(message = "") String> userMedicalConditions;

    @JsonProperty("availableEquipment")
    private List<@NotBlank(message = "") String> userAvailableEquipment;

    @JsonProperty("dailySchedule")
    private List<@NotBlank(message = "") String> userDailySchedule;

    @JsonProperty("dailyDiet")
    private List<@NotBlank(message = "") String> userDailyDiet;
}
