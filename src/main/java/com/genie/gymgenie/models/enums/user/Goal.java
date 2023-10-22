package com.genie.gymgenie.models.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Goal {

    IMPROVE_FITNESS("Improve Fitness"),
    BUILD_MUSCLE("Build Muscle"),
    BURN_FAT("Burn Fat"),
    INCREASE_ENDURANCE("Increase Endurance"),
    BOOST_MENTAL_STRENGTH("Boost Mental Strength"),
    BOOST_LIBIDO("Boost Libido"),
    WEIGHT_LOSS("Weight Loss"),
    BALANCE("Balance"),
    FLEXIBILITY("Flexibility"),
    RELIEVE_STRESS("Relieve Stress"),
    OPTIMIZE_WORKOUTS("Optimize Workouts"),
    INCREASE_ENERGY("Increase Energy"),
    INCREASE_FLEXIBILITY("Increase Flexibility"),
    INCREASE_STRENGTH("Increase Strength"),
    INCREASE_MUSCLE("Increase Muscle"),
    INCREASE_POWER("Increase Power"),
    INCREASE_SPEED("Increase Speed"),
    INCREASE_AGILITY("Increase Agility"),
    RUN_A_5K("Run a 5K"),
    RUN_A_10K("Run a 10K"),
    RUN_A_HALF_MARATHON("Run a Half Marathon"),
    RUN_A_FULL_MARATHON("Run a Full Marathon");

    private final String displayName;
}
