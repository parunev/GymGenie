package com.genie.gymgenie.models.payload.workout;

import com.genie.gymgenie.models.enums.workout.*;
import lombok.Data;

import java.util.List;

@Data
public class WorkoutRequest {

    List<WorkoutAreas> workoutAreas;
    private Objective objective;
    private Duration duration;
    private RepRange repRange;
    private RestRange restRange;
}
