package com.genie.gymgenie.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnergyLevels {

    HIGH("High"),
    MODERATE("Moderate"),
    LOW("Low");

    private final String displayName;
}
