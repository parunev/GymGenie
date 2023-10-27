package com.genie.gymgenie.models.payload.diet;

import com.genie.gymgenie.models.payload.diet.recipe.RecipeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietResponse {

    List<RecipeDto> recipes;
}
