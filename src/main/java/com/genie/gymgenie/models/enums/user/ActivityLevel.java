package com.genie.gymgenie.models.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityLevel {

    SEDENTARY("Little to no exercise, Office job"),
    LIGHTLY_ACTIVE("Light exercise or sports 1-3 days a week"),
    MODERATELY_ACTIVE("Moderate exercise or sports 3-5 days a week"),
    VERY_ACTIVE("Hard exercise or sports 6-7 days a week"),
    EXTRA_ACTIVE("Very hard exercise or sports and a physical job");

    private final String displayName;
}
