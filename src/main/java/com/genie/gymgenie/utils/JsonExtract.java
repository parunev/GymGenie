package com.genie.gymgenie.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genie.gymgenie.models.payload.workout.WorkoutDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonExtract {


    public static WorkoutDto extractInformationFromJSON(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, WorkoutDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
