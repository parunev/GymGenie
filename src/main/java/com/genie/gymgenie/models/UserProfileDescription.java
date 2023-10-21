package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.user.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "USER_PROFILE_DESCRIPTION")
@AttributeOverride(name = "id", column = @Column(name = "USER_PROFILE_DESCRIPTION_ID"))
public class UserProfileDescription extends BaseEntity {

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
    private List<String> userDailyDiet;

    @ManyToOne
    @JoinColumn(name = "USER_PROFILE_ID")
    private UserProfile userProfile;
}
