package com.genie.gymgenie.mapper;

import com.genie.gymgenie.models.Exercise;
import com.genie.gymgenie.models.User;
import com.genie.gymgenie.models.Workout;
import com.genie.gymgenie.models.payload.diet.CalorieIntakeResponse;
import com.genie.gymgenie.models.payload.workout.ExerciseDto;
import com.genie.gymgenie.models.payload.workout.WorkoutRequest;
import com.genie.gymgenie.models.payload.workout.WorkoutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkoutMapper {

    @Mapping(target = "user", source = "user")
    Workout requestToWorkout(WorkoutRequest request, User user);

    @Mapping(target = "calorieIntake", source = "response")
    WorkoutResponse workoutToResponse(Workout workout, CalorieIntakeResponse response);

    @Mapping(target = "workout", source = "workout")
    Exercise requestToExercise(ExerciseDto request, Workout workout);

    ExerciseDto exerciseToDto(Exercise exercise);
}
