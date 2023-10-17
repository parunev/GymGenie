package com.genie.gymgenie.models.enums.fitness;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardioActivity {

    RUNNING("Running"),
    CYCLING("Cycling"),
    SWIMMING("Swimming"),
    JUMP_ROPE("Jump Rope"),
    DANCING("Dancing"),
    HIKING("Hiking"),
    TRAIL_RUNNING("Trail Running"),
    ROWING("Rowing"),
    HIIT("HIIT"),
    STAIR_CLIMBING("Stair Climbing"),
    KICKBOXING("Kickboxing"),
    BOXING("Boxing"),
    MARTIAL_ARTS("Martial Arts"),
    FOOTBALL("Football"),
    BASKETBALL("Basketball");


    private final String displayName;
}
