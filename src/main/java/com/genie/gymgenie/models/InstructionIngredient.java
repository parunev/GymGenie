package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "RECIPE_INSTRUCTION_INGREDIENT")
@AttributeOverride(name = "id", column = @Column(name = "INSTRUCTION_INGREDIENT_ID"))
public class InstructionIngredient extends BaseEntity {

    @Column(name = "INGREDIENT_NAME")
    private String name;

    @Column(name = "INGREDIENT_LOCALIZED_NAME")
    private String localizedName;

    @ManyToOne
    @JoinColumn(name = "INSTRUCTION_STEP_ID")
    private InstructionStep instructionStep;
}
