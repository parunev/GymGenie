package com.genie.gymgenie.models.payload.diet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OurSuggestionDto {

    private String calorieIntakeForToday;
    private CalorieIntakePerMealDto calorieIntakePerMeal;
    private String whyDoWeSuggestThis;
}
