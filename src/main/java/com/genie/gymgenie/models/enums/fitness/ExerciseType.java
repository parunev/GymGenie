package com.genie.gymgenie.models.enums.fitness;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExerciseType {
    BASIC("Basic"),
    CARDIO("Cardio");

    private final String displayName;
}
