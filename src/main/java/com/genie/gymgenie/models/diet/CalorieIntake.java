package com.genie.gymgenie.models.diet;

import com.genie.gymgenie.models.Workout;
import com.genie.gymgenie.models.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "GENIE_CALORIE_INTAKE")
@AttributeOverride(name = "id", column = @Column(name = "CALORIE_INTAKE_ID"))
public class CalorieIntake extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "calorieIntake")
    private List<WeightOption> weightOptions;

    @ManyToOne
    @JoinColumn(name = "WORKOUT_ID")
    private Workout workout;
}
