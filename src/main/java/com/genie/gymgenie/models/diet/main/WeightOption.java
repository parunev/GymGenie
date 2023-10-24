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
@Entity(name = "GENIE_WEIGHT_OPTION")
@AttributeOverride(name = "id", column = @Column(name = "WEIGHT_OPTION_ID"))
public class WeightOption extends BaseEntity{

    @Column(name = "CALORIE_INTAKE_FOR_TODAY")
    private String calorieIntakeForToday;

    @Embedded
    private CalorieIntakePerMeal calorieIntakePerMeal;

    @Column(name = "EXPLANATION",length = 1000)
    private String explanation;
}
