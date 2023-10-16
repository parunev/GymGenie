package com.genie.gymgenie.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobType {

    SEDENTARY("Sedentary"),
    ACTIVE("Active"),
    MANUAL_LABOR("Manual Labor"),
    OTHER("Other");

    private final String displayName;
}
