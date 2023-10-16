package com.genie.gymgenie.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExerciseType {

    CARDIO("Cardio"),
    STRENGTH_TRAINING("Strength Training"),
    FLEXIBILITY("Flexibility"),
    AEROBICS("Aerobics"),
    YOGA("Yoga"),
    SPORTS("Sports"),
    OTHER("Other");

    private final String displayName;
}
