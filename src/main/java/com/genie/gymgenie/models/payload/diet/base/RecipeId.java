package com.genie.gymgenie.models.payload.diet.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeId {

    @JsonProperty("id")
    private Long id;
}
