package com.genie.gymgenie.models.enums.workout;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkoutAreas {

    BACK("Back"),
    BICEPS("Biceps"),
    TRICEPS("Triceps"),
    FOREARMS("Forearms"),
    SHOULDERS("Shoulders"),
    TRAPEZIUS("Trapezius"),
    ABS("Abs"),
    CHEST("Chest"),
    LEGS("Legs"),
    GLUTES("Glutes"),
    ABDOMINALS("Abdominals"),
    ABDUCTORS("Abductors"),
    HAMSTRINGS("Hamstrings"),
    FULL_BODY("Full Body");

    private final String displayName;
}
