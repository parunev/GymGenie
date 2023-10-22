package com.genie.gymgenie.models.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Equipment {

    FULL_GYM("Full Gym"),
    HOME_GYM("Home Gym"),
    BARBELLS("Barbells"),
    DUMBBELLS("Dumbbells"),
    KETTLE_BELLS("Kettle bells"),
    RESISTANCE_BANDS("Resistance Bands"),
    BODY_WEIGHT("Body-weight"),
    MACHINES("Machines"),
    NONE_OF_THE_ABOVE("None of the above");

    private final String displayName;
}
