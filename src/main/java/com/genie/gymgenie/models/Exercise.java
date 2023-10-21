package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.fitness.WeekDay;
import com.genie.gymgenie.models.enums.user.ExerciseType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "EXERCISE")
@AttributeOverride(name = "id", column = @Column(name = "EXERCISE_ID"))
public class Exercise extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "DAY_OF_WEEK", nullable = false)
    private WeekDay dayOfWeek;

    @Enumerated(EnumType.STRING)
    @Column(name = "EXERCISE_TYPE", nullable = false)
    private ExerciseType exerciseType;

    @Column(name = "EXERCISE_NAME", nullable = false)
    private String exerciseName;

    @Column(name = "EXERCISE_DESCRIPTION", nullable = false)
    private String exerciseDescription;

    @Column(name = "EXERCISE_SETS", nullable = false)
    private String exerciseSets;

    @Column(name = "EXERCISE_REPS", nullable = false)
    private String exerciseReps;

    @Column(name = "EXERCISE_REST", nullable = false)
    private String exerciseRest;

    @ManyToOne
    @JoinColumn(name = "FITNESS_REGIME_ID")
    private FitnessRegime regime;
}
