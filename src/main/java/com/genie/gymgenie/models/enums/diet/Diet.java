package com.genie.gymgenie.models.enums.diet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Diet {

    GLUTEN_FREE("Gluten Free", "Eliminating gluten means avoiding wheat, barley, rye, and other gluten-containing" +
            " grains and foods made from them (or that may have been cross contaminated"),
    KETOGENIC("Ketogenic", "The keto diet is based more on the ratio of fat," +
            " protein, and carbs in the diet rather than specific ingredients." +
            " Generally speaking, high fat, protein-rich foods are acceptable and high carbohydrate foods are not." +
            " The formula we use is 55-80% fat content, 15-35% protein content, and under 10% of carbohydrates."),

    VEGETARIAN("Vegetarian", "No ingredients may contain meat or meat by-products, such as bones or gelatin."),
    LACTO_VEGETARIAN("Lacto-Vegetarian", "All ingredients must be vegetarian and none of the ingredients can be or" +
            " contain egg."),
    OVO_VEGETARIAN("Ovo-Vegetarian", "All ingredients must be vegetarian and none of the ingredients can be or contain dairy."),
    VEGAN("Vegan", "No ingredients may contain meat or meat by-products, such as bones or gelatin, nor may they contain eggs, dairy, or honey."),
    PESCETARIAN("Pescetarian", "Everything is allowed except meat and meat by-products - some pescetarians eat eggs and dairy, some do not."),
    PALEO("Paleo", "Allowed ingredients include meat (especially grass fed), fish, eggs, vegetables, some oils " +
            "(e.g. coconut and olive oil), and in smaller quantities, fruit, nuts, and sweet potatoes. " +
            "We also allow honey and maple syrup (popular in Paleo desserts, but strict Paleo followers may disagree). " +
            "Ingredients not allowed include legumes (e.g. beans and lentils), grains, dairy, refined sugar, and processed foods."),

    PRIMAL("Primal", "Very similar to Paleo, except dairy is allowed - think raw and full fat milk, butter, ghee, etc."),

    LOW_FODMAP("Low FODMAP", "FODMAP stands for \"fermentable oligo-, di-, mono-saccharides and polyols\". " +
            "Our ontology knows which foods are considered high in these types of carbohydrates (e.g. legumes, wheat, and dairy products)"),

    WHOLE_30("Whole30", "Allowed ingredients include meat, fish/seafood, eggs, vegetables, fresh fruit, coconut oil, olive oil, " +
            "small amounts of dried fruit and nuts/seeds. Ingredients not allowed include added sweeteners (natural and artificial," +
            " except small amounts of fruit juice), dairy (except clarified butter or ghee), alcohol, grains, legumes (except green beans, " +
            "sugar snap peas, and snow peas), and food additives, such as carrageenan, MSG, and sulfites.");

    private final String displayName;
    private final String description;
}
