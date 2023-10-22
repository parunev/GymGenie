package com.genie.gymgenie.models.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Motivation {

    HEALTH_AND_FITNESS("Health and Fitness"),
    WEIGHT_MANAGEMENT("Weight Management"),
    ACHIEVEMENT_AND_PROGRESS("Achievement and Progress"),
    BECOME_SEXUALLY_ATTRACTIVE("Become Sexually Attractive"),
    SOCIAL_SUPPORT("Social Support"),
    STRESS_RELIEF("Stress Relief"),
    ENJOYMENT("Enjoyment"),
    COMPETITION("Competition"),
    CHALLENGE("Challenge"),
    REHABILITATION("Rehabilitation");

    private final String displayName;
}
