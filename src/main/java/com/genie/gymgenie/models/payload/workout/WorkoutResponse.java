package com.genie.gymgenie.models.payload.workout;

import com.genie.gymgenie.models.enums.workout.Duration;
import com.genie.gymgenie.models.enums.workout.Objective;
import com.genie.gymgenie.models.enums.workout.WorkoutAreas;
import com.genie.gymgenie.models.payload.diet.CalorieIntakeResponse;
import lombok.Data;

import java.util.List;

@Data
public class WorkoutResponse {
    private String workoutName;
    private String motivationalQuote;
    private List<WorkoutAreas> workoutAreas;
    private String hydrationPlan;
    private Objective objective;
    private Duration duration;
    private List<ExerciseDto> exercises;
    private CalorieIntakeResponse calorieIntake;
}
