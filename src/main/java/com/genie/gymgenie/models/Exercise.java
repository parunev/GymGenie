package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "GENIE_EXERCISE")
@AttributeOverride(name = "id", column = @Column(name = "EXERCISE_ID"))
public class Exercise extends BaseEntity {

    @Column(name = "EXERCISE_NAME", nullable = false)
    private String exerciseName;

    @Column(name = "EXERCISE_DESCRIPTION", nullable = false, length = 1000)
    private String exerciseDescription;

    @Column(name = "EXERCISE_SETS", nullable = false)
    private String exerciseSets;

    @Column(name = "EXERCISE_REPS", nullable = false)
    private String exerciseReps;

    @Column(name = "EXERCISE_REST", nullable = false)
    private String exerciseRest;

    @Column(name = "SHORT_YOUTUBE_DEMONSTRATION")
    private String shortYoutubeDemonstration;

    @Column(name = "IN_DEPTH_YOUTUBE_TECHNIQUE")
    private String inDepthYoutubeTechnique;

    @ManyToOne
    @JoinColumn(name = "WORKOUT_ID")
    private Workout workout;
}
