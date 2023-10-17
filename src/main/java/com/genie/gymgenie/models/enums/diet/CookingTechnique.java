package com.genie.gymgenie.models.enums.diet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CookingTechnique {
    GRILLING("Grilling"),
    BAKING("Baking"),
    SAUTEING("Sautéing"),
    MICROWAVE("Microwave"),
    OVEN("Oven"),
    STOVETOP("Stovetop");

    private final String displayName;
}
