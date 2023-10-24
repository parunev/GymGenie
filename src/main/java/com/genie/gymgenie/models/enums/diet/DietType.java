package com.genie.gymgenie.models.enums.diet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DietType {

    BREAKFAST("breakfast"),
    LUNCH("main course"),
    SNACK("snack"),
    SHAKE("shake"),
    DINNER("main course");

    private final String displayName;
}
