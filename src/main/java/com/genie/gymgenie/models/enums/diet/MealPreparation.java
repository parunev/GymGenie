package com.genie.gymgenie.models.enums.diet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MealPreparation {
    READY_TO_EAT("Ready to Eat"),
    MEAL_KITS("Meal Kits"),
    PRE_COOKED_AND_STORED("Pre-cooked and Stored");

    private final String displayName;
}
