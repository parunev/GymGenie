package com.genie.gymgenie.models.payload.diet.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FoodNoun {

    @JsonProperty("name")
    private String name;
}
