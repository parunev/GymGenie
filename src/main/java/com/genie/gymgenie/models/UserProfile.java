package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "USER_PROFILE")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_FITNESS_LEVEL", nullable = false)
    private FitnessLevel userFitnessLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_WORKOUT_LOCATION", nullable = false)
    private WorkoutLocation userWorkoutLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_EXERCISE_TYPE", nullable = false)
    private ExerciseType userExerciseType;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STRESS_LEVEL", nullable = false)
    private StressLevel userStressLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_JOB_TYPE", nullable = false)
    private JobType userJobType;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ENERGY_LEVEL", nullable = false)
    private EnergyLevels userEnergyLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_SLEEP_QUALITY", nullable = false)
    private SleepQuality userSleepQuality;

    @ElementCollection
    @CollectionTable(name = "USER_SHORT_TERM_GOALS")
    private List<String> userShortTermGoals;

    @ElementCollection
    @CollectionTable(name = "USER_LONG_TERM_GOALS")
    private List<String> userLongTermGoals;

    @ElementCollection
    @CollectionTable(name = "USER_DIETARY_RESTRICTIONS")
    private List<String> userDietaryRestrictions;

    @ElementCollection
    @CollectionTable(name = "USER_MEDICAL_CONDITIONS")
    private List<String> userMedicalConditions;

    @ElementCollection
    @CollectionTable(name = "USER_AVAILABLE_EQUIPMENT")
    private List<String> userAvailableEquipment;

    @ElementCollection
    @CollectionTable(name = "USER_DAILY_SCHEDULE")
    private List<String> userDailySchedule;

    @ElementCollection
    @CollectionTable(name = "USER_DAILY_DIET")
    private List<String> userDailyDiet; // current

    @OneToMany(mappedBy = "userProfile")
    private List<MedicalHistory> userMedicalHistory;

    @OneToMany(mappedBy = "userProfile")
    private List<Injury> userInjuries;
}
