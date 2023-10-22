package com.genie.gymgenie.models.enums.workout;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Duration {

    DURATION_20("20 minutes"),
    DURATION_30("30 minutes"),
    DURATION_40("40 minutes"),
    DURATION_50("50 minutes"),
    DURATION_60("60 minutes"),
    DURATION_70("70 minutes"),
    DURATION_80("80 minutes"),
    DURATION_90("90 minutes");

    private final String durationName;
}
