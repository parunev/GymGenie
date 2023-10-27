package com.genie.gymgenie.models.payload.diet.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StepDto {
    private int number;
    private String step;
    private List<IngredientDto> ingredients;
    private List<EquipmentDto> equipment;
}
