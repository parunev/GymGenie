package com.genie.gymgenie.models.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkoutFrequency {

    ONE("1 X"),
    TWO("2 X"),
    THREE("3 X"),
    FOUR("4 X"),
    FIVE("5 X"),
    SIX("6 X"),
    SEVEN("7 X");

    private final String displayName;
}
