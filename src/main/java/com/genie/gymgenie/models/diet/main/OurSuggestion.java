package com.genie.gymgenie.models.diet.main;

import com.genie.gymgenie.models.commons.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "GENIE_OUR_SUGGESTION")
@AttributeOverride(name = "id", column = @Column(name = "OUR_SUGGESTION_ID"))
public class OurSuggestion extends BaseEntity {

    private String calorieIntakeForToday;

    @Embedded
    private CalorieIntakePerMeal calorieIntakePerMeal;

    @Column(name = "WHY_DO_WE_SUGGEST_THIS",length = 1000)
    private String whyDoWeSuggestThis;
}
