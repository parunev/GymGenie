package com.genie.gymgenie.models.enums.user;

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
