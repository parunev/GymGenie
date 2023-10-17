package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.user.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "USER_PROFILE")
@AttributeOverride(name = "id", column = @Column(name = "USER_PROFILE_ID"))
public class UserProfile extends BaseEntity {

    @Column(name = "USER_AGE", nullable = false)
    private Integer userAge;

    @Column(name = "USER_WEIGHT", nullable = false)
    private Double userWeight;

    @Column(name = "USER_HEIGHT", nullable = false)
    private Double userHeight;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_GENDER", nullable = false)
    private Gender userGender;

    @Column(name = "USER_EXERCISE_PER_WEEK", nullable = false)
    private Integer userExercisePerWeek; // number of days

    @Column(name = "USER_AVG_WORKOUT_DURATION", nullable = false)
    private Integer userAvgWorkoutDuration; // in minutes

    @Column(name = "USER_DIETARY_CHANGE", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean userOpenToDietaryChange;

    @Column(name = "USER_SKIP_MEALS", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean userSkipMeals;

    @Column(name = "USER_OPEN_TO_PURCHASE", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean userOpenToPurchase; // equipment related

    @Column(name = "USER_MEALS_PER_DAY", nullable = false)
    private Integer userMealsPerDay; // how many meals does a user consume per day

    @OneToMany(mappedBy = "userProfile")
    private List<MedicalHistory> userMedicalHistory;

    @OneToMany(mappedBy = "userProfile")
    private List<Injury> userInjuries;

    @OneToMany(mappedBy = "userProfile")
    private List<FitnessRegime> fitnessRegimes;
}
