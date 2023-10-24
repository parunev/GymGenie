package com.genie.gymgenie.models.payload.diet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalorieIntakePerMealDto {
    private String minCalories;
    private String maxCalories;
}
