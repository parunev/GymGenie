package com.genie.gymgenie.models.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkoutAreas {

    BACK("Back"),
    ARMS("Arms"),
    SHOULDERS("Shoulders"),
    ABS("Abs"),
    CHEST("Chest"),
    LEGS("Legs"),
    GLUTES("Glutes"),
    FULL_BODY("Full Body");

    private final String displayName;
}
