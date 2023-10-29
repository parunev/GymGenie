package com.genie.gymgenie.models.payload.workout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.genie.gymgenie.models.enums.workout.Duration;
import com.genie.gymgenie.models.enums.workout.Objective;
import com.genie.gymgenie.models.enums.workout.WorkoutAreas;
import com.genie.gymgenie.models.payload.diet.CalorieIntakeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutResponse {
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String workoutName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String motivationalQuote;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<WorkoutAreas> workoutAreas;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String hydrationPlan;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Objective objective;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Duration duration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ExerciseDto> exercises;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CalorieIntakeResponse calorieIntake;
}
