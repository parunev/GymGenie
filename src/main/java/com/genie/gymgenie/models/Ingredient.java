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
@Entity(name = "RECIPE_INGREDIENT")
@AttributeOverride(name = "id", column = @Column(name = "INGREDIENT_ID"))
public class Ingredient extends BaseEntity {

    @Column(name = "AISLE")
    private String aisle;

    @Column(name = "CONSISTENCY")
    private String consistency;

    @Column(name = "INGREDIENT_NAME")
    private String name;

    @Column(name = "INGREDIENT_NAME_CLEAN")
    private String nameClean;

    @Column(name = "INGREDIENT_ORIGINAL")
    private String original;

    @Column(name = "INGREDIENT_ORIGINAL_NAME")
    private String originalName;

    @Column(name = "INGREDIENT_AMOUNT")
    private Double amount;

    @Column(name = "INGREDIENT_UNIT")
    private String unit;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;
}
