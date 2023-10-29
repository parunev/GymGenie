package com.genie.gymgenie.models.payload.diet.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDto {
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int healthScore;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ExtendedIngredientDto> extendedIngredients;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int readyInMinutes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int servings;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sourceUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String image;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String summary;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String instructions;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AnalyzedInstructionsDto> analyzedInstructions;
}


