package com.genie.gymgenie.models.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {

    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String displayName;
}
