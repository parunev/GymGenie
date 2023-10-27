package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "RECIPE_INSTRUCTION_STEP")
@AttributeOverride(name = "id", column = @Column(name = "INSTRUCTION_STEP_ID"))
public class InstructionStep extends BaseEntity {

    @Column(name = "STEP_NUMBER")
    private int number;

    @Column(name = "STEP_INFO", length = 2000)
    private String step;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "INSTRUCTION_STEP_ID")
    private List<InstructionIngredient> ingredients;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "INSTRUCTION_STEP_ID")
    private List<InstructionEquipment> equipment;

    @ManyToOne
    @JoinColumn(name = "RECIPE_INSTRUCTION_ID")
    private RecipeInstruction recipeInstruction;
}
