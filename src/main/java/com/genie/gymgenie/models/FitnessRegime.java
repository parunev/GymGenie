package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.fitness.CardioActivity;
import com.genie.gymgenie.models.enums.fitness.CardioFrequency;
import com.genie.gymgenie.models.enums.fitness.ExerciseFrequency;
import com.genie.gymgenie.models.enums.fitness.IntensityLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "USER_FITNESS_REGIME")
@AttributeOverride(name = "id", column = @Column(name = "FITNESS_REGIME_ID"))
public class FitnessRegime extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "REGIME_INTESITY_LEVEL", nullable = false)
    private IntensityLevel intensityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "REGIME_EXERCISE_FREQUENCY", nullable = false)
    private ExerciseFrequency frequency;

    @OneToMany(mappedBy = "regime")
    private List<Exercise> exercises;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = CardioActivity.class)
    @CollectionTable(name = "CARDIO_ACTIVITIES")
    private List<CardioActivity> cardioActivities;

    @Enumerated(EnumType.STRING)
    @Column(name = "REGIME_CARDIO_FREQUENCY", nullable = false)
    private CardioFrequency cardioFrequency;

    @Column(name = "PROGRESSION_GUIDE", length = 5000)
    private String progression;

    @Column(name = "HYDRATION_PLAN", length = 5000)
    private String hydrationPlan;

    @ElementCollection
    @CollectionTable(name = "INJURY_PREVENTION_TIPS")
    private List<String> injuryPreventionTips;

    @ManyToOne
    @JoinColumn(name = "USER_PROFILE_ID")
    private UserProfile userProfile;
}
