package com.genie.gymgenie.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genie.gymgenie.genie.GenieAgent;
import com.genie.gymgenie.mapper.RecipeMapper;
import com.genie.gymgenie.models.*;
import com.genie.gymgenie.models.enums.diet.Cuisine;
import com.genie.gymgenie.models.enums.diet.Intolerance;
import com.genie.gymgenie.models.payload.diet.RecipeRequest;
import com.genie.gymgenie.models.payload.diet.RecipeResponse;
import com.genie.gymgenie.models.payload.diet.base.FoodNoun;
import com.genie.gymgenie.models.payload.diet.recipe.RecipeDto;
import com.genie.gymgenie.models.payload.diet.base.RecipeId;
import com.genie.gymgenie.repositories.*;
import com.genie.gymgenie.security.GenieLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.genie.gymgenie.genie.UserPrompts.recipesPrompt;
import static com.genie.gymgenie.security.CurrentUser.getCurrentUserDetails;
import static com.genie.gymgenie.security.ErrorMessages.*;
import static com.genie.gymgenie.utils.ExceptionThrower.extractException;
import static com.genie.gymgenie.utils.ExceptionThrower.resourceException;
import static com.genie.gymgenie.utils.JsonExtract.extractFoodNounResponseFromJSON;

@Service
@Validated
@RequiredArgsConstructor
public class RecipeService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final CalorieIntakeRepository calorieIntakeRepository;
    private final WeightOptionRepository weightOptionRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeInstructionRepository recipeInstructionRepository;
    private final IngredientRepository ingredientRepository;
    private final GenieAgent foodNounAgent;
    private final RestTemplate restTemplate;
    private final RecipeMapper recipeMapper;
    private final GenieLogger genie = new GenieLogger(RecipeService.class);

    @Value("${api.spoonacular.url}")
    private String baseUrl;

    @Value("${api.spoonacular.key}")
    private String apiKey;

    public RecipeDto retrieveSingleUserRecipe(Long recipeId){
        User user = userRepository.findByUsername(getCurrentUserDetails().getUsername())
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", getCurrentUserDetails().getUsername());
                    throw resourceException(NO_ACCOUNT_FOUND.formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        Recipe recipe = recipeRepository.findByIdAndWorkoutUser(recipeId, user)
                .orElseThrow(() -> {
                    genie.warn("Recipe with id {} not found", recipeId);
                    throw resourceException("Recipe with id(%d) not found".formatted(recipeId), HttpStatus.NOT_FOUND);
                });

        return recipeMapper.mapToRecipeDto(recipe);
    }

    public Page<RecipeDto> retrieveAllUserRecipes(Long workoutId, Double healthScoreMin, Double healthScoreMax, String recipeTitle, String recipeSummary, Pageable pageable){
        User user = userRepository.findByUsername(getCurrentUserDetails().getUsername())
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", getCurrentUserDetails().getUsername());
                    throw resourceException(NO_ACCOUNT_FOUND.formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        return recipeRepository.findAllByWorkoutUser(user, workoutId, healthScoreMin, healthScoreMax, recipeTitle, recipeSummary, pageable)
                .map(recipeMapper::mapToRecipeDto);
    }

    public RecipeResponse generateDiet(RecipeRequest recipeRequest, Long workoutId){
        User user = userRepository.findByUsername(getCurrentUserDetails().getUsername())
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", getCurrentUserDetails().getUsername());
                    throw resourceException(NO_ACCOUNT_FOUND.formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        Workout workout = workoutRepository.findByIdAndUser(workoutId, user)
                .orElseThrow(() -> {
                    genie.warn("Workout with id {} not found", workoutId);
                    throw resourceException(NO_WORKOUT_FOUND.formatted(workoutId), HttpStatus.NOT_FOUND);
                });

        CalorieIntake calorieIntake = calorieIntakeRepository.findByWorkout(workout)
                .orElseThrow(() -> {
                    genie.warn("CalorieIntake for workout with id {} not found", workoutId);
                    throw resourceException(NO_CALORIE_INTAKE_FOUND.formatted(workoutId), HttpStatus.NOT_FOUND);
                });

        WeightOption weightOption = weightOptionRepository.findByCalorieIntakeAndName(calorieIntake, recipeRequest.getWeightOptionType())
                .orElseThrow(() -> {
                    genie.warn("WeightOption with name {} not found", recipeRequest.getWeightOptionType());
                    throw resourceException(NO_WEIGHT_OPTION_FOUND.formatted(recipeRequest.getWeightOptionType()), HttpStatus.NOT_FOUND);
                });

        String prompt = recipesPrompt(workout, weightOption, recipeRequest);
        String foodNounsJson = foodNounAgent.foodNouns(prompt);
        List<FoodNoun> foodNouns = extractFoodNounResponseFromJSON(foodNounsJson);

        if (foodNouns == null || foodNouns.isEmpty()) {
            genie.warn("No food nouns found for prompt {}", prompt);
            throw resourceException(FOOD_NOUN_PROBLEM, HttpStatus.BAD_REQUEST);
        }

        List<RecipeId> recipeIds = getRecipeIds(foodNouns, recipeRequest, weightOption);
        List<Recipe> recipes = findAndSaveRecipes(recipeIds, workout);

        List<RecipeDto> toReturn = mapResponse(recipes);

        return RecipeResponse.builder()
                .recipes(toReturn)
                .build();
    }

    private List<RecipeDto> mapResponse(List<Recipe> recipes) {
        List<RecipeDto> toReturn = new ArrayList<>();
        for (Recipe recipe : recipes){
            RecipeDto recipeDto = RecipeDto.builder()
                    .id(recipe.getId())
                    .healthScore(recipe.getHealthScore().intValue())
                    .title(recipe.getTitle())
                    .readyInMinutes(recipe.getReadyInMinutes())
                    .sourceUrl(recipe.getSourceUrl())
                    .image(recipe.getImage())
                    .summary(clean(recipe.getSummary()))
                    .instructions(clean(recipe.getInstructions()))
                    .build();
            toReturn.add(recipeDto);
        }

        return toReturn;
    }

    private String clean(String summary) {
        Pattern pattern = Pattern.compile("<.*?>");
        Matcher matcher = pattern.matcher(summary);
        return matcher.replaceAll("");
    }

    private List<Recipe> findAndSaveRecipes(List<RecipeId> selectedRecipeIds, Workout workout) {
        List<Recipe> recipes = new ArrayList<>();

        for (RecipeId recipeId : selectedRecipeIds){
            genie.info("Getting recipe with id {}", recipeId.getId());
            String url = baseUrl + "/informationBulk?apiKey=" + apiKey
                    + "&ids=" + recipeId.getId()
                    + "&includeNutrition=false";

            String jsonResponse = restTemplate.getForObject(url, String.class);

            try{
                ObjectMapper objectMapper = new ObjectMapper();
                List<RecipeDto> recipeDtoList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
                RecipeDto recipeDto = recipeDtoList.get(0);
                Recipe recipe = Recipe.builder()
                        .apiId(recipeId.getId())
                        .healthScore((double) recipeDto.getHealthScore())
                        .title(recipeDto.getTitle())
                        .readyInMinutes(recipeDto.getReadyInMinutes())
                        .servings(recipeDto.getServings())
                        .sourceUrl(recipeDto.getSourceUrl())
                        .image(recipeDto.getImage())
                        .summary(recipeDto.getSummary())
                        .instructions(recipeDto.getInstructions())
                        .build();
                recipeRepository.save(recipe);

                List<Ingredient> ingredientList = processIngredients(recipeDto, recipe);
                List<RecipeInstruction> recipeInstructions = new ArrayList<>();

                for (int i = 0; i < recipeDto.getAnalyzedInstructions().size(); i++) {
                    List<InstructionStep> instructionSteps = processInstructionSteps(recipeDto, i);
                    RecipeInstruction recipeInstruction = RecipeInstruction.builder()
                            .steps(instructionSteps)
                            .recipe(recipe)
                            .build();
                    recipeInstructionRepository.save(recipeInstruction);
                    recipeInstructions.add(recipeInstruction);
                }

                recipe.setExtendedIngredients(ingredientList);
                recipe.setAnalyzedInstructions(recipeInstructions);
                recipe.setWorkout(workout);
                recipeRepository.save(recipe);
                recipes.add(recipe);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        genie.info("Recipes saved successfully");
        return recipes;
    }

    private List<InstructionStep> processInstructionSteps(RecipeDto recipeDto, int i) {
        List<InstructionStep> toReturn = new ArrayList<>();
        for (int j = 0; j < recipeDto.getAnalyzedInstructions().get(i).getSteps().size(); j++) {
            List<InstructionIngredient> instructionIngredients = processInstructionIngredients(recipeDto, i, j);
            List<InstructionEquipment> instructionEquipments = processInstructionEquipments(recipeDto, i, j);

            InstructionStep instructionStep = InstructionStep.builder()
                    .step(recipeDto.getAnalyzedInstructions().get(i).getSteps().get(j).getStep())
                    .number(recipeDto.getAnalyzedInstructions().get(i).getSteps().get(j).getNumber())
                    .ingredients(instructionIngredients)
                    .equipment(instructionEquipments)
                    .build();
            toReturn.add(instructionStep);
        }
        return toReturn;
    }

    private List<InstructionEquipment> processInstructionEquipments(RecipeDto recipeDto, int i, int j) {
        List<InstructionEquipment> toReturn = new ArrayList<>();
        for (int k = 0; k < recipeDto.getAnalyzedInstructions().get(i).getSteps().get(j).getEquipment().size(); k++) {
            InstructionEquipment instructionEquipment = InstructionEquipment
                    .builder()
                    .name(recipeDto.getAnalyzedInstructions().get(i).getSteps().get(j).getEquipment().get(k).getName())
                    .localizedName(recipeDto.getAnalyzedInstructions().get(i).getSteps().get(j).getEquipment().get(k).getLocalizedName())
                    .build();
            toReturn.add(instructionEquipment);
        }
        return toReturn;
    }

    private List<InstructionIngredient> processInstructionIngredients(RecipeDto recipeDto, int i, int j) {
        List<InstructionIngredient> toReturn = new ArrayList<>();
        for (int k = 0; k < recipeDto.getAnalyzedInstructions().get(i).getSteps().get(j).getIngredients().size(); k++) {
            InstructionIngredient ingredient = InstructionIngredient.builder()
                    .name(recipeDto.getAnalyzedInstructions().get(i).getSteps().get(j).getIngredients().get(k).getName())
                    .localizedName(recipeDto.getAnalyzedInstructions().get(i).getSteps().get(j).getIngredients().get(k).getLocalizedName())
                    .build();
            toReturn.add(ingredient);
        }
        return toReturn;
    }

    private List<Ingredient> processIngredients(RecipeDto recipeDto, Recipe recipe) {
        List<Ingredient> toReturn = new ArrayList<>();
        for (int i = 0; i < recipeDto.getExtendedIngredients().size(); i++) {
            Ingredient ingredient = Ingredient.builder()
                    .aisle(recipeDto.getExtendedIngredients().get(i).getAisle())
                    .consistency(recipeDto.getExtendedIngredients().get(i).getConsistency())
                    .name(recipeDto.getExtendedIngredients().get(i).getName())
                    .nameClean(recipeDto.getExtendedIngredients().get(i).getNameClean())
                    .original(recipeDto.getExtendedIngredients().get(i).getOriginal())
                    .originalName(recipeDto.getExtendedIngredients().get(i).getOriginalName())
                    .amount(recipeDto.getExtendedIngredients().get(i).getAmount())
                    .unit(recipeDto.getExtendedIngredients().get(i).getUnit())
                    .recipe(recipe)
                    .build();
            ingredientRepository.save(ingredient);
            toReturn.add(ingredient);
        }
        return toReturn;
    }

    private List<RecipeId> getRecipeIds(List<FoodNoun> foodNouns, RecipeRequest request, WeightOption weightOption) {
        String cuisines = createCommaSeparatedString(request.getPreferredCuisines(), Cuisine::getDisplayName);
        String dislikedCuisines = createCommaSeparatedString(request.getDislikedCuisines(), Cuisine::getDisplayName);
        String intolerances = createCommaSeparatedString(request.getIntolerance(), Intolerance::getDisplayName);

        List<RecipeId> recipeIds = new ArrayList<>();
        for (FoodNoun foodNoun : foodNouns) {
            genie.info("Getting recipe ids for food noun {}", foodNoun.getName());

            String url = constructUrl(foodNoun, cuisines, dislikedCuisines, intolerances, weightOption);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            try{
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                JsonNode results = jsonNode.get("results");

                for (JsonNode result : results) {
                    recipeIds.add(new RecipeId(result.get("id").asLong()));
                }
            } catch (Exception e) {
                genie.error("(List<RecipeId>) Exception while extracting information from JSON! Exception: {}, Message: {}"
                        ,e, e.getMessage());
                throw extractException(e.getMessage());
            }
        }

        return recipeIds;
    }

    private <T> String createCommaSeparatedString(List<T> list, Function<T, String> mapper) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        return list.stream()
                .map(item -> mapper.apply(item).toLowerCase())
                .collect(Collectors.joining(","));
    }

    private String constructUrl(FoodNoun foodNoun, String cuisines, String dislikedCuisines, String intolerances, WeightOption weightOption){
        StringBuilder url = new StringBuilder();
        url.append(baseUrl).append("/complexSearch").append("?apiKey=").append(apiKey);
        url.append("&query=").append(foodNoun.getName());
        if (!cuisines.isEmpty()) {
            url.append("&cuisine=").append(cuisines);
        }
        if (!dislikedCuisines.isEmpty()) {
            url.append("&excludeCuisine=").append(dislikedCuisines);
        }
        if (!intolerances.isEmpty()) {
            url.append("&intolerances=").append(intolerances);
        }
        url.append("&instructionsRequired=true");
        url.append("&addRecipeInformation=true");
        url.append("&minCalories=").append(weightOption.getMinCaloriesPerMeal());
        url.append("&maxCalories=").append(weightOption.getMaxCaloriesPerMeal());
        url.append("&number=2");

        genie.info("URL: {}", url.toString());
        return url.toString();
    }
}
