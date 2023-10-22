package com.genie.gymgenie.models.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FitnessLevel {

    BEGINNER("I'm new or have only tried it for a bit"),
    INTERMEDIATE("I've been doing this for a while"),
    ADVANCED("I've stuck to a balanced routine for years!"),
    PROFESSIONAL("I'm a professional athlete");

    private final String displayName;
}
