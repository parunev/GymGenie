package com.genie.gymgenie.models.diet.main;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CalorieIntakePerMeal {

    private String minCalories;
    private String maxCalories;
}
