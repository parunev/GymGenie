package com.genie.gymgenie.models.payload.workout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {
    @JsonProperty("exerciseName")
    private String exerciseName;
    @JsonProperty("exerciseDescription")
    private String exerciseDescription;
    @JsonProperty("exerciseSets")
    private String exerciseSets;
    @JsonProperty("exerciseReps")
    private String exerciseReps;
    @JsonProperty("exerciseRest")
    private String exerciseRest;
    @JsonProperty("shortYoutubeDemonstration")
    private String shortYoutubeDemonstration;
    @JsonProperty("inDepthYoutubeTechnique")
    private String inDepthYoutubeTechnique;
}
