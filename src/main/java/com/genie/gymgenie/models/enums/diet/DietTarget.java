package com.genie.gymgenie.models.enums.diet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DietTarget {

    MAINTAIN_WEIGHT("Maintain Weight"),
    MILD_WEIGHT_LOSS("Mild Weight Loss"),
    WEIGHT_LOSS("Weight Loss"),
    EXTREME_WEIGHT_LOSS("Extreme Weight Loss");

    private final String displayName;
}
