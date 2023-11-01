package com.genie.gymgenie.mapper;

import com.genie.gymgenie.models.Recipe;
import com.genie.gymgenie.models.payload.diet.recipe.RecipeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeDto mapToRecipeDto(Recipe recipe);
}
