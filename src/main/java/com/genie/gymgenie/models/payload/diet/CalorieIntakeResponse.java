package com.genie.gymgenie.models.payload.diet;

import lombok.Data;

import java.util.List;

@Data
public class CalorieIntakeResponse {

    private List<WeightOptionDto> weightOptions;
}
