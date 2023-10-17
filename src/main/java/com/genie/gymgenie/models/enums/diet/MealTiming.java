package com.genie.gymgenie.models.enums.diet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MealTiming {
    SMALLER_FREQUENT_MEALS("Smaller Frequent Meals"),
    THREE_LARGER_MEALS("Three Larger Meals"),
    SPECIFIC_TIMING_PREFERENCES("Specific Timing Preferences");

    private final String displayName;
}
