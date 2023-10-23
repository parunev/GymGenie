package com.genie.gymgenie.models.enums.workout;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Objective {

    MUSCLE_GROWTH("build muscle"),
    STRENGTH_TRAINING("build strength"),
    CALORIE_BURN("burn calories"),
    WEIGHT_LOSS("lose weight"),
    ENDURANCE("increase endurance"),
    TONING("tone muscles");

    private final String objectiveName;
}
