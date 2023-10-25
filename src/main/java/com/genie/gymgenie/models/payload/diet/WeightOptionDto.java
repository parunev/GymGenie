package com.genie.gymgenie.models.payload.diet;

import lombok.Data;

@Data
public class WeightOptionDto {
    private String name;
    private String calorieIntakeForToday;
    private String minCaloriesPerMeal;
    private String maxCaloriesPerMeal;
    private String explanation;
}
