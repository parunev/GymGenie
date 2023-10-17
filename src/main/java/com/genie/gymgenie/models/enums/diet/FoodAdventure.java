package com.genie.gymgenie.models.enums.diet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FoodAdventure {
    OPEN_TO_NEW_FOODS("Open to New Foods"),
    PREFER_FAMILIAR_CHOICES("Prefer Familiar Choices");

    private final String displayName;
}
