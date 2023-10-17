package com.genie.gymgenie.models.enums.diet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MealType {
    QUICK_AND_EASY("Quick and Easy"),
    GOURMET("Gourmet"),
    HOMEY_COMFORT_FOOD("Homey Comfort Food");

    private final String displayName;
}
