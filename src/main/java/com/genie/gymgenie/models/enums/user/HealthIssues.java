package com.genie.gymgenie.models.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HealthIssues {

    POST_COVID_RECOVERY("Post-COVID Recovery"),
    CANT_DO_THE_JUMPS("Can't do the jumps(Asthma, Can't breathe properly)"),
    BACK_OR_HERNIA("Back problems or some sort of hernia"),
    ARMS_AND_SHOULDER("Arms and shoulder problems"),
    KNEE_PROBLEMS("Knee problems"),
    HIP_PROBLEMS("Hip problems"),
    ANKLE_PROBLEMS("Ankle problems"),
    NONE_OF_THE_ABOVE("None of the above");

    private final String displayName;
}
