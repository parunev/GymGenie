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
@Entity(name = "RECIPE_INSTRUCTION_EQUIPMENT")
@AttributeOverride(name = "id", column = @Column(name = "INSTRUCTION_EQUIPMENT_ID"))
public class InstructionEquipment extends BaseEntity {

    @Column(name = "EQUIPMENT_NAME")
    private String name;

    @Column(name = "EQUIPMENT_LOCALIZED_NAME")
    private String localizedName;

    @ManyToOne
    @JoinColumn(name = "INSTRUCTION_STEP_ID")
    private InstructionStep instructionStep;
}
