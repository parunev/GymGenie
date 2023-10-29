package com.genie.gymgenie.genie;

import com.genie.gymgenie.models.*;
import com.genie.gymgenie.models.enums.user.Equipment;
import com.genie.gymgenie.models.enums.user.Goal;
import com.genie.gymgenie.models.enums.user.HealthIssues;
import com.genie.gymgenie.models.enums.user.Motivation;
import com.genie.gymgenie.models.enums.workout.WorkoutAreas;
import com.genie.gymgenie.models.payload.diet.RecipeRequest;
import com.genie.gymgenie.models.payload.workout.WorkoutRequest;
import com.genie.gymgenie.security.GenieLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPrompts {

    private final static GenieLogger genie = new GenieLogger(UserPrompts.class);

    public static String workoutPrompt(WorkoutRequest request, User user, Health health) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("I'm a %d-years-old, %s, my height is %d and my weight is %d."
                .formatted(user.getAge(), user.getGender().getDisplayName(), user.getHeight(), user.getWeight())).append("\n");

        prompt.append("My target weight currently is %d, and my goals are %s."
                .formatted(user.getTargetWeight(), user.getGoals())).append("\n");

        prompt.append("My current fitness level is %s, and my activity level is %s."
                .formatted(user.getFitnessLevel().getDisplayName(), user.getActivityLevel().getDisplayName())).append("\n");

        prompt.append("As motivations I consider: ");
        for (Motivation motivation : user.getMotivations()){
            prompt.append(motivation.getDisplayName()).append("\n");
        }
        prompt.append("\n");

        if (user.getHealthIssues().contains(HealthIssues.NONE_OF_THE_ABOVE)){
            prompt.append("I don't have any health issues.").append("\n");
        } else {
            prompt.append("I have the following health issues: %s."
                    .formatted(user.getHealthIssues())).append("\n");
        }

        if (user.getEquipment().contains(Equipment.NONE_OF_THE_ABOVE)){
            prompt.append("I don't have any equipment.").append("\n");
        } else {
            prompt.append("I have the following equipment: ");
            for (Equipment equipment : user.getEquipment()){
                prompt.append("%s".formatted(equipment.getDisplayName())).append(", ");
            }
        }

        prompt.append("\n").append("My body mass index is %s."
                .formatted(health.getBodyMassIndex())).append("\n");

        prompt.append("My total daily energy expenditure is %s."
                .formatted(health.getTotalDailyEnergyExpenditure())).append("\n");

        prompt.append("My average body fat is %s."
                .formatted(health.getAvgBodyFat().getDisplayName())).append("\n");

        prompt.append("Usually I workout %s week."
                .formatted(user.getWorkoutFrequency().getDisplayName())).append("\n");

        prompt.append("My preferred workout days are %s."
                .formatted(user.getWorkoutDays())).append("\n");

        prompt.append("For today's workout I want to focus on:");
        for (WorkoutAreas workoutAreas : request.getWorkoutAreas()){
            prompt.append("%s".formatted(workoutAreas.getDisplayName())).append(", ");
        }
        prompt.append("\n").append("The objective of my workout is to %s."
                .formatted(request.getObjective().getObjectiveName())).append("\n");

        prompt.append("I want my workout to not last more than %s."
                .formatted(request.getDuration().getDurationName())).append("\n");

        prompt.append("I prefer to do %s reps per set."
                .formatted(request.getRepRange().getDisplayName())).append("\n");

        prompt.append("I prefer to rest %s between sets."
                .formatted(request.getRestRange().getDisplayName())).append("\n");


        genie.info("Prompt: {}", prompt.toString());
        return prompt.toString();
    }

    public static  String calorieIntakePrompt(User user, Health health, Workout workout) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("I'm a %d-years-old, %s, my height is %d and my weight is %d."
                .formatted(user.getAge(), user.getGender().getDisplayName(), user.getHeight(), user.getWeight())).append("\n");

        prompt.append("My target weight currently is %d, and my goals are:."
                .formatted(user.getTargetWeight())).append("\n");

        for (Goal goal : user.getGoals()){
            prompt.append(goal.getDisplayName()).append("\n");
        }
        prompt.append("My current activity level is %s."
                .formatted(user.getActivityLevel().getDisplayName())).append("\n");

        prompt.append("My current fitness level is %s."
                .formatted(user.getFitnessLevel().getDisplayName())).append("\n");

        prompt.append("My BMI (body mass index) is %s."
                .formatted(health.getBodyMassIndex())).append("\n");

        prompt.append("My TDEE (total daily energy expenditure) is %s."
                .formatted(health.getTotalDailyEnergyExpenditure())).append("\n");

        prompt.append("My average body fat is %s."
                .formatted(health.getAvgBodyFat().getDisplayName())).append("\n");

        prompt.append("Today's workout details:").append("\n");
        prompt.append("Workout objective: %s."
                .formatted(workout.getObjective().getObjectiveName())).append("\n");

        prompt.append("Workout duration: %s."
                .formatted(workout.getDuration().getDurationName())).append("\n");

        prompt.append("Workout hydration plan: %s."
                .formatted(workout.getHydrationPlan())).append("\n");

        prompt.append("Workout rep range: %s."
                .formatted(workout.getRepRange().getDisplayName())).append("\n");

        prompt.append("Workout rest range: %s."
                .formatted(workout.getRestRange().getDisplayName())).append("\n");

        prompt.append("Workout exercises:").append("\n");
        for (Exercise exercise : workout.getExercises()){
            prompt.append(exercise.getExerciseName()).append("\n");
        }

        genie.info("Prompt: {}", prompt.toString());
        return prompt.toString();
    }

    public static String recipesPrompt(Workout workout, WeightOption weightOption, RecipeRequest recipeRequest) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("My workout objective is %s with durations of %s minutes."
                .formatted(workout.getObjective().getObjectiveName(), workout.getDuration())).append("\n");

        prompt.append("The hydration plan I followed during the day was: %s"
                .formatted(workout.getHydrationPlan())).append("\n");

        prompt.append("The workout rep range I followed was %s with rest range of %s"
                .formatted(workout.getRepRange().getDisplayName(), workout.getRestRange().getDisplayName())).append("\n");

        prompt.append("The exercises I did were:").append("\n");

        for (int i = 0; i < workout.getExercises().size(); i++) {
            prompt.append("%d. %s, reps: %s, sets: %s".formatted(i + 1, workout.getExercises().get(i).getExerciseName(),
                    workout.getExercises().get(i).getExerciseReps(), workout.getExercises().get(i).getExerciseSets())).append("\n");
        }

        prompt.append("For today I want to consume %s calories."
                .formatted(weightOption.getCalorieIntakeForToday())).append("\n");

        prompt.append("With maximum %s calories and minimum %s calories."
                .formatted(weightOption.getMaxCaloriesPerMeal(), weightOption.getMinCaloriesPerMeal())).append("\n");

        if (recipeRequest.getPreferredCuisines() != null){
            prompt.append("My diet plan for today needs to include cuisines from %s."
                    .formatted(recipeRequest.getPreferredCuisines())).append("\n");
        }

        if(recipeRequest.getDislikedCuisines() != null){
            prompt.append("And needs to exclude cuisines from %s."
                    .formatted(recipeRequest.getDislikedCuisines())).append("\n");
        }

        if (recipeRequest.getDiet() != null){
            prompt.append("Currently I follow this diet %s and the diet description is the following:"
                    .formatted(recipeRequest.getDiet().getDisplayName())).append("\n");
            prompt.append("%s"
                    .formatted(recipeRequest.getDiet().getDescription()));
        }

        if(recipeRequest.getIntolerance() != null){
            prompt.append("I have intolerances toward %s."
                    .formatted(recipeRequest.getIntolerance())).append("\n");
        }

        prompt.append("Please provide me with 5 food nouns that I can eat today.");

        genie.info("Prompt: {}", prompt.toString());
        return prompt.toString();
    }
}
