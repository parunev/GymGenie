package com.genie.gymgenie.models.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BodyFat {

    ESSENTIAL("1% to 6% Body fat"),
    ATHLETES("6% to 14% Body fat"),
    FITNESS("14% to 18% Body fat"),
    ACCEPTABLE("18% to 25% Body fat"),
    OBESITY("25% to 32% Body fat");

    private final String displayName;
}
