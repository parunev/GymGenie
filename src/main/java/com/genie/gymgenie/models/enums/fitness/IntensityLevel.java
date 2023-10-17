package com.genie.gymgenie.models.enums.fitness;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IntensityLevel {
    LIGHT("Light"),
    MODERATE("Moderate"),
    HEAVY("Heavy");

    private final String displayName;
}
