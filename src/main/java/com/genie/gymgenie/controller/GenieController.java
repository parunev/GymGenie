package com.genie.gymgenie.controller;

import com.genie.gymgenie.config.RateLimitConfig;
import com.genie.gymgenie.models.payload.diet.DietRequest;
import com.genie.gymgenie.models.payload.diet.DietResponse;
import com.genie.gymgenie.models.payload.workout.WorkoutRequest;
import com.genie.gymgenie.models.payload.workout.WorkoutResponse;
import com.genie.gymgenie.service.DietService;
import com.genie.gymgenie.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.genie.gymgenie.utils.ExceptionThrower.TOO_MANY_REQUESTS;
import static com.genie.gymgenie.utils.ExceptionThrower.authException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genie/v1")
public class GenieController {

    private final WorkoutService workoutService;
    private final RateLimitConfig rateLimit;
    private final DietService dietService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/workout")
    public ResponseEntity<WorkoutResponse> generateWorkout(@RequestBody WorkoutRequest request){

        if (rateLimit.oneBucket().tryConsume(1)){
            return ResponseEntity.ok(workoutService.generateWorkout(request));
        }

        throw authException(TOO_MANY_REQUESTS.formatted("to generate a workout 30 seconds"), HttpStatus.TOO_MANY_REQUESTS);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/diet/{workoutId}")
    public ResponseEntity<DietResponse> generateDiet(@RequestBody DietRequest request, @PathVariable("workoutId") Long workoutId){

        if(rateLimit.oneBucket().tryConsume(1)){
            return ResponseEntity.ok(dietService.generateDiet(request, workoutId));
        }

        throw authException(TOO_MANY_REQUESTS.formatted("to generate a diet 30 seconds"), HttpStatus.TOO_MANY_REQUESTS);
    }
}
