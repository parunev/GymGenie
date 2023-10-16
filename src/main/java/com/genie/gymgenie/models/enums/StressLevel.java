package com.genie.gymgenie.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StressLevel {

    LOW("Low"),
    MODERATE("Moderate"),
    HIGH("High");

    private final String displayName;
}
