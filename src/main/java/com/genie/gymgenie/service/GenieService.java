package com.genie.gymgenie.service;

import com.genie.gymgenie.genie.GenieAgent;
import com.genie.gymgenie.mapper.IntakeMapper;
import com.genie.gymgenie.mapper.WorkoutMapper;
import com.genie.gymgenie.models.Exercise;
import com.genie.gymgenie.models.Health;
import com.genie.gymgenie.models.User;
import com.genie.gymgenie.models.Workout;
import com.genie.gymgenie.models.diet.CalorieIntake;
import com.genie.gymgenie.models.enums.user.Equipment;
import com.genie.gymgenie.models.enums.user.Goal;
import com.genie.gymgenie.models.enums.user.HealthIssues;
import com.genie.gymgenie.models.enums.user.Motivation;
import com.genie.gymgenie.models.enums.workout.WorkoutAreas;
import com.genie.gymgenie.models.payload.diet.CalorieIntakeResponse;
import com.genie.gymgenie.models.payload.workout.ExerciseDto;
import com.genie.gymgenie.models.payload.workout.WorkoutDto;
import com.genie.gymgenie.models.payload.workout.WorkoutRequest;
import com.genie.gymgenie.models.payload.workout.WorkoutResponse;
import com.genie.gymgenie.repositories.*;
import com.genie.gymgenie.security.GenieLogger;
import dev.ai4j.openai4j.OpenAiHttpException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static com.genie.gymgenie.security.CurrentUser.getCurrentUserDetails;
import static com.genie.gymgenie.utils.ExceptionThrower.resourceException;
import static com.genie.gymgenie.utils.JsonExtract.extractCalorieIntakeResponseFromJSON;
import static com.genie.gymgenie.utils.JsonExtract.extractInformationFromJSON;

@Service
@Validated
@RequiredArgsConstructor
public class GenieService {

    private final GenieAgent workoutAgent;
    private final GenieAgent calorieAgent;
    private final UserRepository userRepository;
    private final HealthRepository healthRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final CalorieIntakeRepository calorieIntakeRepository;
    private final WorkoutMapper workoutMapper;
    private final IntakeMapper intakeMapper;
    private final GenieLogger genie = new GenieLogger(GenieService.class);

    public WorkoutResponse generateWorkout(@Valid WorkoutRequest request){
        genie.info("Generating workout for user {}", getCurrentUserDetails().getUsername());
        User user = userRepository.findByUsername(getCurrentUserDetails().getUsername())
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", getCurrentUserDetails().getUsername());
                    throw resourceException("No account associated with this username(%s) found.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });
        Health health =  healthRepository.findByUserUsername(user.getUsername())
                .orElseThrow(() -> {
                    genie.warn("Health details for username {} not found", getCurrentUserDetails().getUsername());
                    throw resourceException("No health details associated with this username(%s) found.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        genie.info("Saving initial workout template to database");
        Workout workout = workoutMapper.requestToWorkout(request, user);
        workoutRepository.save(workout);

        genie.info("Generating prompt for workout");
        String prompt = generateWorkoutPrompt(request, user, health);

        String genieOutput;
        try{
          genieOutput = workoutAgent.workout(prompt);
        } catch (OpenAiHttpException e){
            genie.warn("OpenAI API returned an error: {}", e.getMessage());
            throw resourceException("OpenAI API returned an error: %s".formatted(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        WorkoutDto workoutDto = extractInformationFromJSON(genieOutput);

        genie.info("Saving generated workout to database");
        if (workoutDto != null){
            workout.setWorkoutName(workoutDto.getWorkoutName());
            workout.setMotivationalQuote(workoutDto.getMotivationalQuote());
            workout.setHydrationPlan(workoutDto.getHydrationPlan());

            List<Exercise> exerciseList = new ArrayList<>();
            List<ExerciseDto> exerciseTransfer = workoutDto.getExercises();

            for (ExerciseDto exerciseDto : exerciseTransfer) {
                Exercise exercise = workoutMapper.requestToExercise(exerciseDto, workout);
                exerciseRepository.save(exercise);
                exerciseList.add(exercise);
            }

            workout.setExercises(exerciseList);
            genie.info("Workout saved successfully");
            workoutRepository.save(workout);
        }

        genie.info("Generating prompt for calorie intake");
        prompt = generateCalorieIntakePrompt(user, health, workout);
        try{
            genieOutput = calorieAgent.calorieIntake(prompt);
        } catch (OpenAiHttpException e){
            genie.warn("OpenAI API returned an error: {}", e.getMessage());
            throw resourceException("OpenAI API returned an error: %s".formatted(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        genie.info("Saving calorie intake to database");
        CalorieIntake calorieIntake = extractCalorieIntakeResponseFromJSON(genieOutput);
        if (calorieIntake != null){
            calorieIntake.setWorkout(workout);
            calorieIntakeRepository.save(calorieIntake);
        }

        CalorieIntakeResponse calorieIntakeResponse = intakeMapper.mapToCalorieIntake(calorieIntake);

        return workoutMapper.workoutToResponse(workout, calorieIntakeResponse);
    }

    private String generateCalorieIntakePrompt(User user, Health health, Workout workout) {
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
        prompt.append("My BMI (body mass index) is %s.".formatted(health.getBodyMassIndex())).append("\n");
        prompt.append("My TDEE (total daily energy expenditure) is %s.".formatted(health.getTotalDailyEnergyExpenditure())).append("\n");
        prompt.append("My average body fat is %s.".formatted(health.getAvgBodyFat().getDisplayName())).append("\n");
        prompt.append("Today's workout details:").append("\n");
        prompt.append("Workout objective: %s.".formatted(workout.getObjective().getObjectiveName())).append("\n");
        prompt.append("Workout duration: %s.".formatted(workout.getDuration().getDurationName())).append("\n");
        prompt.append("Workout hydration plan: %s.".formatted(workout.getHydrationPlan())).append("\n");
        prompt.append("Workout rep range: %s.".formatted(workout.getRepRange().getDisplayName())).append("\n");
        prompt.append("Workout rest range: %s.".formatted(workout.getRestRange().getDisplayName())).append("\n");
        prompt.append("Workout exercises:").append("\n");
        for (Exercise exercise : workout.getExercises()){
            prompt.append(exercise.getExerciseName()).append("\n");
        }

        genie.info("Prompt: {}", prompt.toString());
        return prompt.toString();
    }

    private String generateWorkoutPrompt(WorkoutRequest request, User user, Health health) {
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
            prompt.append("I have the following health issues: %s.".formatted(user.getHealthIssues())).append("\n");
        }

        if (user.getEquipment().contains(Equipment.NONE_OF_THE_ABOVE)){
            prompt.append("I don't have any equipment.").append("\n");
        } else {
            prompt.append("I have the following equipment: ");
            for (Equipment equipment : user.getEquipment()){
                prompt.append("%s".formatted(equipment.getDisplayName())).append(", ");
            }
        }

        prompt.append("\n").append("My body mass index is %s.".formatted(health.getBodyMassIndex())).append("\n");
        prompt.append("My total daily energy expenditure is %s.".formatted(health.getTotalDailyEnergyExpenditure())).append("\n");
        prompt.append("My average body fat is %s.".formatted(health.getAvgBodyFat().getDisplayName())).append("\n");

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
}
