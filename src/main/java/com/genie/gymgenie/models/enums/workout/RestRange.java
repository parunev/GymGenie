package com.genie.gymgenie.models.enums.workout;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RestRange {

    ZERO_TO_THIRTY_SECONDS("0-30 seconds"),
    THIRTY_TO_SIXTY_SECONDS("30-60 seconds"),
    ONE_TO_TWO_MINUTES("1-2 minutes"),
    TWO_TO_THREE_MINUTES("2-3 minutes"),
    THREE_TO_FOUR_MINUTES("3-4 minutes"),
    FOUR_TO_FIVE_MINUTES("4-5 minutes"),
    FIVE_TO_SIX_MINUTES("5-6 minutes");

    private final String displayName;
}
