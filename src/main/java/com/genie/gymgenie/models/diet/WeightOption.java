package com.genie.gymgenie.models.diet;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.diet.WeightOptionType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "GENIE_CALORIE_OPTION")
@AttributeOverride(name = "id", column = @Column(name = "CALORIE_OPTION_ID"))
public class WeightOption extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "WEIGHT_OPTION_NAME")
    private WeightOptionType name;

    @Column(name = "CALORIE_INTAKE_FOR_TODAY")
    private String calorieIntakeForToday;

    @Column(name = "MAX_CALORIES_PER_MEAL")
    private String maxCaloriesPerMeal;

    @Column(name = "MIN_CALORIES_PER_MEAL")
    private String minCaloriesPerMeal;

    @Column(name = "EXPLANATION",length = 1000)
    private String explanation;

    @ManyToOne
    @JoinColumn(name = "CALORIE_INTAKE_ID")
    private CalorieIntake calorieIntake;
}
