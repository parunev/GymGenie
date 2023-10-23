package com.genie.gymgenie.models.payload.workout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutDto {

    @JsonProperty("workoutName")
    private String workoutName;
    @JsonProperty("motivationalQuote")
    private String motivationalQuote;
    @JsonProperty("hydrationPlan")
    private String hydrationPlan;
    @JsonProperty("exercises")
    private List<ExerciseDto> exercises;
}
