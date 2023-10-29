package com.genie.gymgenie.models.payload.diet;

import com.genie.gymgenie.models.enums.diet.Cuisine;
import com.genie.gymgenie.models.enums.diet.Diet;
import com.genie.gymgenie.models.enums.diet.Intolerance;
import com.genie.gymgenie.models.enums.diet.WeightOptionType;
import lombok.Data;

import java.util.List;

@Data
public class RecipeRequest {

    private WeightOptionType weightOptionType;
    private List<Cuisine> preferredCuisines;
    private List<Cuisine> dislikedCuisines;
    private Diet diet;
    private List<Intolerance> intolerance;
}
