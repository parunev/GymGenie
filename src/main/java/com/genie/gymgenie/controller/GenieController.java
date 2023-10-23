package com.genie.gymgenie.controller;

import com.genie.gymgenie.models.payload.workout.WorkoutRequest;
import com.genie.gymgenie.models.payload.workout.WorkoutResponse;
import com.genie.gymgenie.service.GenieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genie/v1")
public class GenieController {

    private final GenieService genieService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/workout")
    public ResponseEntity<WorkoutResponse> generateWorkout(@RequestBody WorkoutRequest request){
        return ResponseEntity.ok(genieService.generateWorkout(request));
    }
}
