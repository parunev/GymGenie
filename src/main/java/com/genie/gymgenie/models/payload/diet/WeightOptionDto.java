package com.genie.gymgenie.models.payload.diet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeightOptionDto {

    private String calorieIntakeForToday;
    private CalorieIntakePerMealDto calorieIntakePerMeal;
    private String explanation;
}
