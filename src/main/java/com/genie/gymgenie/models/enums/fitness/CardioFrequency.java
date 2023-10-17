package com.genie.gymgenie.models.enums.fitness;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardioFrequency {
    TWO_HOURS("2 Hours a week"),
    THREE_HOURS("3 Hours a week"),
    FOUR_HOURS("4 Hours a week"),
    FIVE_HOURS("5 Hours a week"),
    SIX_HOURS("6 Hours a week"),
    CUSTOM("Custom");
    private final String displayName;
}
