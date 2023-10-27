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
@Entity(name = "RECIPE_INSTRUCTION")
@AttributeOverride(name = "id", column = @Column(name = "RECIPE_INSTRUCTION_ID"))
public class RecipeInstruction extends BaseEntity {

    @Column(name = "INSTRUCTION_NAME")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "RECIPE_INSTRUCTION_ID")
    private List<InstructionStep> steps;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;
}
