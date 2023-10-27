package com.genie.gymgenie.service;

import com.genie.gymgenie.genie.GenieAgent;
import com.genie.gymgenie.mapper.IntakeMapper;
import com.genie.gymgenie.mapper.WorkoutMapper;
import com.genie.gymgenie.models.*;
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

import static com.genie.gymgenie.genie.UserPrompts.calorieIntakePrompt;
import static com.genie.gymgenie.genie.UserPrompts.workoutPrompt;
import static com.genie.gymgenie.security.CurrentUser.getCurrentUserDetails;
import static com.genie.gymgenie.utils.ExceptionThrower.resourceException;
import static com.genie.gymgenie.utils.JsonExtract.extractCalorieIntakeResponseFromJSON;
import static com.genie.gymgenie.utils.JsonExtract.extractInformationFromJSON;

@Service
@Validated
@RequiredArgsConstructor
public class WorkoutService {

    private final GenieAgent workoutAgent;
    private final GenieAgent calorieAgent;
    private final UserRepository userRepository;
    private final HealthRepository healthRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final CalorieIntakeRepository calorieIntakeRepository;
    private final WorkoutMapper workoutMapper;
    private final IntakeMapper intakeMapper;
    private final GenieLogger genie = new GenieLogger(WorkoutService.class);

    public WorkoutResponse generateWorkout(@Valid WorkoutRequest request){
        genie.info("Generating workout for user {}", getCurrentUserDetails().getUsername());
        User user = userRepository.findByUsername(getCurrentUserDetails().getUsername())
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", getCurrentUserDetails().getUsername());
                    throw resourceException("No account associated with this username(%s) found.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        Health health = healthRepository.findByUserUsername(user.getUsername())
                .orElseThrow(() -> {
                    genie.warn("Health details for username {} not found", getCurrentUserDetails().getUsername());
                    throw resourceException("No health details associated with this username(%s) found.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        genie.info("Saving initial workout template to database");
        Workout workout = workoutMapper.requestToWorkout(request, user);
        workoutRepository.save(workout);

        genie.info("Generating prompt for workout");
        String prompt = workoutPrompt(request, user, health);

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
        prompt = calorieIntakePrompt(user, health, workout);
        try{
            genieOutput = calorieAgent.calorieIntake(prompt);
        } catch (OpenAiHttpException e){
            genie.warn("OpenAI API returned an error: {}", e.getMessage());
            throw resourceException("OpenAI API returned an error: %s".formatted(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        genie.info("Saving calorie intake to database");
        CalorieIntake calorieIntake = extractCalorieIntakeResponseFromJSON(genieOutput);
        if (calorieIntake != null){
            for (int i = 0; i < calorieIntake.getWeightOptions().size(); i++){
                calorieIntake.getWeightOptions().get(i).setCalorieIntake(calorieIntake);
            }
            calorieIntake.setWorkout(workout);
            calorieIntakeRepository.save(calorieIntake);
        }

        CalorieIntakeResponse calorieIntakeResponse = intakeMapper.mapToCalorieIntake(calorieIntake);

        return workoutMapper.workoutToResponse(workout, calorieIntakeResponse);
    }
}
