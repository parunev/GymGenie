package com.genie.gymgenie.models.enums.workout;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RepRange {

    LOW("Between 1 and 5"),
    NORMAL("Between 5 and 10"),
    HIGH("Between 10 and 15"),
    VERY_HIGH("More than 15");

    private final String displayName;
}
