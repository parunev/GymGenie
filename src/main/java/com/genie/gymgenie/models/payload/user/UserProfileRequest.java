package com.genie.gymgenie.models.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genie.gymgenie.models.enums.user.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileRequest {

    @NotNull
    @Min(value = 0, message = "Age must be a positive number")
    @JsonProperty("age")
    private Integer userAge;

    @NotNull
    @Positive(message = "Weight must be a positive number")
    @JsonProperty("weight")
    private Double userWeight;

    @NotNull
    @Positive(message = "Height must be a positive number")
    @JsonProperty("height")
    private Double userHeight;

    @NotNull
    @JsonProperty("gender")
    private Gender userGender;

    @NotNull
    @Min(value = 0, message = "Exercise per week must be a non-negative number")
    @JsonProperty("exercisePerWeek")
    private Integer userExercisePerWeek;

    @NotNull
    @Min(value = 0, message = "Average workout duration must be a non-negative number")
    @JsonProperty("avgWorkoutDuration")
    private Integer userAvgWorkoutDuration;

    @NotNull
    @JsonProperty("openToDietaryChange")
    private Boolean userOpenToDietaryChange;

    @NotNull
    @JsonProperty("skipMeals")
    private Boolean userSkipMeals;

    @NotNull
    @JsonProperty("openToPurchase")
    private Boolean userOpenToPurchase;

    @NotNull
    @Min(value = 0, message = "Meals per day must be a non-negative number")
    @JsonProperty("mealPerDay")
    private Integer userMealsPerDay;

    @JsonProperty("description")
    private UserProfileDescriptionRequest userProfileDescription;
    private List<InjuryRequest> injuries;
    private List<MedicalHistoryRequest> medicalHistories;


    // {
    //  "age": 30,
    //  "weight": 70.5,
    //  "height": 175.0,
    //  "gender": "MALE",
    //  "exercisePerWeek": 3,
    //  "avgWorkoutDuration": 60,
    //  "openToDietaryChange": true,
    //  "skipMeals": false,
    //  "openToPurchase": true,
    //  "mealsPerDay": 3,
    //  "userProfileDescription": {
    //    "fitnessLevel": "MODERATE",
    //    "workoutLocation": "GYM",
    //    "exerciseType": "CARDIO",
    //    "stressLevel": "LOW",
    //    "jobType": "OFFICE",
    //    "energyLevel": "HIGH",
    //    "sleepQuality": "GOOD",
    //    "shortTermGoals": ["Lose weight", "Build muscle"],
    //    "longTermGoals": ["Maintain a healthy lifestyle", "Run a marathon"],
    //    "dietaryRestrictions": ["Gluten-free", "Vegetarian"],
    //    "medicalConditions": ["Allergies", "Asthma"],
    //    "availableEquipment": ["Dumbbells", "Treadmill"],
    //    "dailySchedule": ["Morning jog", "Evening workout"],
    //    "dailyDiet": ["Breakfast: Oatmeal", "Lunch: Salad"]
    //  },
    //  "injuries": [
    //    {
    //      "description": "Sprained ankle",
    //      "occurred": "2023-04-15"
    //    },
    //    {
    //      "description": "Back pain",
    //      "occurred": "2022-08-10"
    //    }
    //  ],
    //  "medicalHistories": [
    //    {
    //      "description": "No significant medical history"
    //    }
    //  ]
    //}
}
