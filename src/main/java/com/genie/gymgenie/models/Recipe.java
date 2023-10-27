package com.genie.gymgenie.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Entity(name = "GENIE_RECIPE")
@JsonIgnoreProperties(ignoreUnknown = true)
@AttributeOverride(name = "id", column = @Column(name = "RECIPE_ID"))
public class Recipe extends BaseEntity {

    @Column(name = "RECIPE_API_ID")
    private Long apiId;

    @Column(name = "HEALTH_SCORE")
    private Double healthScore;

    @Column(name = "RECIPE_TITLE")
    private String title;

    @Column(name = "READY_IN_MINUTES")
    private Integer readyInMinutes;

    @Column(name = "SERVINGS")
    private Integer servings;

    @Column(name = "RECIPE_SOURCE_URL")
    private String sourceUrl;

    @Column(name = "RECIPE_IMAGE")
    private String image;

    @Column(name = "RECIPE_SUMMARY", length = 5000)
    private String summary;

    @Column(name = "RECIPE_INSTRUCTIONS", length = 5000)
    private String instructions;

    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> extendedIngredients;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeInstruction> analyzedInstructions;

    @ManyToOne
    @JoinColumn(name = "WORKOUT_ID")
    private Workout workout;
}
