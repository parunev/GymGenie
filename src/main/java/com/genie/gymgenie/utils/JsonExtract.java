package com.genie.gymgenie.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genie.gymgenie.genie.UserPrompts;
import com.genie.gymgenie.models.CalorieIntake;
import com.genie.gymgenie.models.payload.diet.base.FoodNoun;
import com.genie.gymgenie.models.payload.workout.WorkoutDto;
import java.util.Collections;

import com.genie.gymgenie.security.GenieLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.genie.gymgenie.utils.ExceptionThrower.extractException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonExtract {

    private final static GenieLogger genie = new GenieLogger(UserPrompts.class);

    public static WorkoutDto extractInformationFromJSON(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, WorkoutDto.class);
        } catch (Exception e) {
            genie.error("(WORKOUT DTO) Exception while extracting information from JSON! Exception: {}, Message: {}"
                    ,e, e.getMessage());
            throw extractException(e.getMessage());
        }
    }

    public static CalorieIntake extractCalorieIntakeResponseFromJSON(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, CalorieIntake.class);
        } catch (Exception e) {
            genie.error("(CALORIE INTAKE) Exception while extracting information from JSON! Exception: {}, Message: {}"
                    ,e, e.getMessage());
            throw extractException(e.getMessage());
        }
    }

    public static List<FoodNoun> extractFoodNounResponseFromJSON(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode foodNounsNode = rootNode.get("foodNouns");

            if (foodNounsNode != null && foodNounsNode.isArray()) {
                return objectMapper.readValue(foodNounsNode.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, FoodNoun.class));
            }

        } catch (Exception e) {
            genie.error("(List<FoodNoun>) Exception while extracting information from JSON! Exception: {}, Message: {}"
                    ,e, e.getMessage());
            throw extractException(e.getMessage());
        }

        return Collections.emptyList();
    }


}
