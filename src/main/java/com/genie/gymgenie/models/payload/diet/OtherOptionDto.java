package com.genie.gymgenie.models.payload.diet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtherOptionDto {

    private WeightOptionDto maintainWeight;
    private WeightOptionDto mildWeightLoss;
    private WeightOptionDto weightLoss;
    private WeightOptionDto extremeWeightLoss;
}
