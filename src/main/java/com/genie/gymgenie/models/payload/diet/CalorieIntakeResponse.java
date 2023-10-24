package com.genie.gymgenie.models.payload.diet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalorieIntakeResponse {

    private OurSuggestionDto ourSuggestions;
    private OtherOptionDto otherOptions;

}
