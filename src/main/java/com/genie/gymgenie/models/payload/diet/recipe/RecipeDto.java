package com.genie.gymgenie.models.payload.diet.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDto {
    private int healthScore;
    private List<ExtendedIngredientDto> extendedIngredients;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String sourceUrl;
    private String image;
    private String summary;
    private String instructions;
    private List<AnalyzedInstructionsDto> analyzedInstructions;
}


