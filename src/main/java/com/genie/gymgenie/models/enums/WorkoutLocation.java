package com.genie.gymgenie.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkoutLocation {

    GYM("Gym"),
    OUTDOORS("Outdoors"),
    HOME("Home");

    private final String displayName;
}
