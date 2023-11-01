package com.genie.gymgenie.controller;

import com.genie.gymgenie.models.enums.workout.Objective;
import com.genie.gymgenie.models.enums.workout.WorkoutAreas;
import com.genie.gymgenie.models.payload.diet.RecipeRequest;
import com.genie.gymgenie.models.payload.diet.RecipeResponse;
import com.genie.gymgenie.models.payload.diet.recipe.RecipeDto;
import com.genie.gymgenie.models.payload.workout.WorkoutRequest;
import com.genie.gymgenie.models.payload.workout.WorkoutResponse;
import com.genie.gymgenie.service.RecipeService;
import com.genie.gymgenie.service.WorkoutService;
import com.genie.gymgenie.utils.RateLimitExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genie/v1")
public class GenieController {

    private final WorkoutService workoutService;
    private final RecipeService recipeService;
    private final RateLimitExecutor executor;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/workout")
    public ResponseEntity<WorkoutResponse> generateWorkout(@RequestBody WorkoutRequest request){
       return new ResponseEntity<>(
                    executor.executeGenie(() -> workoutService.generateWorkout(request),
                            "Workout generation", "to generate a workout 30 seconds"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/workouts")
    public ResponseEntity<Page<WorkoutResponse>> retrieveAllUserWorkouts(@RequestParam(value = "workoutName", required = false) String workoutName,
                                                                         @RequestParam(value = "workoutArea", required = false) WorkoutAreas workoutArea,
                                                                         @RequestParam(value = "objective", required = false) Objective objective,
                                                                         Pageable pageable){
        return new ResponseEntity<>(
                executor.executeGenie(() -> workoutService.retrieveAllUserWorkouts(workoutName, workoutArea, objective, pageable),
                        "Workout retrieval", "to retrieve workouts 30 seconds"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/workout/{workoutId}")
    public ResponseEntity<WorkoutResponse> retrieveWorkoutById(@PathVariable("workoutId") Long workoutId){
        return new ResponseEntity<>(
                executor.executeGenie(() -> workoutService.retrieveSingleUserWorkout(workoutId),
                        "Workout retrieval", "to retrieve a workout 30 seconds"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/recipe/{workoutId}")
    public ResponseEntity<RecipeResponse> generateRecipes(@RequestBody RecipeRequest request, @PathVariable("workoutId") Long workoutId){
        return new ResponseEntity<>(
                executor.executeGenie(() -> recipeService.generateDiet(request, workoutId),
                        "Diet generation", "to generate a diet 30 seconds"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/recipes")
    public ResponseEntity<Page<RecipeDto>> retrieveAllUserRecipes(
            @RequestParam(value = "workoutId", required = false) Long workoutId,
            @RequestParam(value = "healthScoreMin", required = false) Double healthScoreMin,
            @RequestParam(value = "healthScoreMax", required = false) Double healthScoreMax,
            @RequestParam(value = "recipeTitle", required = false) String recipeTitle,
            @RequestParam(value = "recipeSummary", required = false) String recipeSummary,
            @PageableDefault(size = 2) Pageable pageable){
        return new ResponseEntity<>(
                executor.executeGenie(() -> recipeService.retrieveAllUserRecipes(workoutId, healthScoreMin, healthScoreMax, recipeTitle, recipeSummary, pageable),
                        "Recipe retrieval", "to retrieve recipes 30 seconds"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<RecipeDto> retrieveRecipeById(@PathVariable("recipeId") Long recipeId){
        return new ResponseEntity<>(
                executor.executeGenie(() -> recipeService.retrieveSingleUserRecipe(recipeId),
                        "Recipe retrieval", "to retrieve a recipe 30 seconds"), HttpStatus.OK);
    }
}
