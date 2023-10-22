package com.genie.gymgenie.models.enums.workout;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Objective {

    MUSCLE_GROWTH("Muscle Growth"),
    STRENGTH_TRAINING("Strength Training"),
    CALORIE_BURN("Calorie Burn"),
    WEIGHT_LOSS("Weight Loss"),
    ENDURANCE("Endurance"),
    TONING("Toning");

    private final String objectiveName;
}
