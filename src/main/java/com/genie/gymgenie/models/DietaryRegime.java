package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.diet.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "USER_DIETARY_REGIME")
@AttributeOverride(name = "id", column = @Column(name = "DIETARY_REGIME_ID"))
public class DietaryRegime extends BaseEntity {

    @ElementCollection
    @CollectionTable(name = "FOOD_PREFERENCES")
    private List<String> foodPreferences;

    @ElementCollection
    @CollectionTable(name = "RESTRICTIONS")
    private List<String> dietaryRestrictions;

    @Enumerated(EnumType.STRING)
    @Column(name = "HEALTH_GOALS", nullable = false)
    private HealthGoal healthGoal;

    @Enumerated(EnumType.STRING)
    @Column(name = "MEAL_TIMING", nullable = false)
    private MealTiming mealTiming;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = CookingTechnique.class)
    @CollectionTable(name = "COOKING_TECHNIQUES")
    private List<CookingTechnique> cookingTechniques;

    @Column(name = "CULTURAL_INFLUENCE", length = 2000)
    private String culturalInfluence;

    @ElementCollection
    @CollectionTable(name = "ALLERGIES_SENSITIVITIES")
    private List<String> allergiesSensitivities;

    @Column(name = "BUDGET_AVAILABILITY", nullable = false)
    private BigDecimal budgetAvailability;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = MealType.class)
    @CollectionTable(name = "MEAL_TYPE")
    private List<MealType> mealTypes;

    @Enumerated(EnumType.STRING)
    @Column(name = "FOOD_ADVENTURE", nullable = false)
    private FoodAdventure foodAdventure;

    @Enumerated(EnumType.STRING)
    @Column(name = "MEAL_PREPARATION", nullable = false)
    private MealPreparation mealPreparation;

    @ManyToOne
    private UserProfile userProfile;

    @OneToOne
    private FitnessRegime fitnessRegime;
}
