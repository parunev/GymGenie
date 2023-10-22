package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.workout.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "WORKOUT")
@AttributeOverride(name = "id", column = @Column(name = "WORKOUT_ID"))
public class Workout extends BaseEntity {

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
}
