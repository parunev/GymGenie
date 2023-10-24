package com.genie.gymgenie.models.diet;

import com.genie.gymgenie.models.Workout;
import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.diet.main.OtherOption;
import com.genie.gymgenie.models.diet.main.OurSuggestion;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "GENIE_CALORIE_INTAKE")
@AttributeOverride(name = "id", column = @Column(name = "CALORIE_INTAKE_ID"))
public class CalorieIntake extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OUR_SUGGESTION_ID")
    private OurSuggestion ourSuggestions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OTHER_OPTION_ID")
    private OtherOption otherOptions;

    @ManyToOne
    @JoinColumn(name = "WORKOUT_ID")
    private Workout workout;
}
