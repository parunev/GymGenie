package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.workout.WorkoutAreas;
import com.genie.gymgenie.models.enums.workout.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "GENIE_WORKOUT")
@AttributeOverride(name = "id", column = @Column(name = "WORKOUT_ID"))
public class Workout extends BaseEntity {

    @Column(name = "WORKOUT_NAME")
    private String workoutName;

    @Column(name = "MOTIVATIONAL_QUOTE", length = 1000)
    private String motivationalQuote;

    @ElementCollection
    @CollectionTable(name = "WORKOUT_AREAS", joinColumns = @JoinColumn(name = "WORKOUT_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "WORKOUT_AREAS", nullable = false)
    private List<WorkoutAreas> workoutAreas;

    @Column(name = "HYDRATION_PLAN", length = 1000)
    private String hydrationPlan;

    @Enumerated(EnumType.STRING)
    @Column(name = "OBJECTIVE", nullable = false)
    private Objective objective;

    @Enumerated(EnumType.STRING)
    @Column(name = "DURATION", nullable = false)
    private Duration duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "REP_RANGE", nullable = false)
    private RepRange repRange;

    @Enumerated(EnumType.STRING)
    @Column(name = "REST_RANGE", nullable = false)
    private RestRange restRange;

    @OneToMany(mappedBy = "workout")
    private List<Exercise> exercises;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
