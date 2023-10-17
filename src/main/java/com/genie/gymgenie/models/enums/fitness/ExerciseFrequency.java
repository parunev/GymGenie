package com.genie.gymgenie.models.enums.fitness;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExerciseFrequency {
    DAILY("Daily"),
    THREE_TIMES_A_WEEK("Three Times a Week"),
    TWO_TIMES_A_WEEK("Two Times a Week"),
    WEEKLY("Weekly"),
    CUSTOM("Custom");

    private final String displayName;
}
