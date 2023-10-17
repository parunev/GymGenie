package com.genie.gymgenie.models.enums.diet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HealthGoal {
    LOSE_WEIGHT("Lose Weight"),
    GAIN_MUSCLE("Gain Muscle"),
    MAINTAIN_WEIGHT("Maintain Weight"),
    BALANCED_DIET("Balanced Diet");

    private final String displayName;
}
