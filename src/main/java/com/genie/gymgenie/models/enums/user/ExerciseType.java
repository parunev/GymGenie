package com.genie.gymgenie.models.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExerciseType {

    AEROBIC("Aerobic Exercise"),
    STRENGTH("Strength Training"),
    YOGA("Yoga"),
    WALKING("Walking"),
    SWIMMING("Swimming"),
    CYCLING("Cycling"),
    RUNNING("Running"),
    AEROBICS("Aerobics"),
    DANCE("Dance"),
    CIRCUIT("Circuit Training"),
    WEIGHT("Weight Training"),
    HIKING("Hiking"),
    HIIT("High Intensity Interval Training"),
    FLEXIBILITY("Flexibility Exercise"),
    ELLIPTICAL("Elliptical"),
    WEIGHTLIFTING("Weightlifting"),
    BOXING("Boxing"),
    JOGGING("Jogging"),
    INTERVAL("Interval Training"),
    KICKBOXING("Kickboxing"),
    ANAEROBIC("Anaerobic Exercise"),
    GYMNASTICS("Gymnastics"),
    ISOMETRIC("Isometric Exercise"),
    ABDOMINAL("Abdominal Exercise"),
    CARDIO("Cardio");

    private final String displayName;
}
