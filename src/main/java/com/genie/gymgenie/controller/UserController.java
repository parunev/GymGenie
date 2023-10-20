package com.genie.gymgenie.controller;

import com.genie.gymgenie.config.RateLimitConfig;
import com.genie.gymgenie.models.payload.user.profile.UserProfileRequest;
import com.genie.gymgenie.models.payload.user.profile.updatewrapper.ProfileUpdateWrapper;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.payload.ApiResponse;
import com.genie.gymgenie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.genie.gymgenie.security.CurrentUser.getCurrentUserDetails;
import static com.genie.gymgenie.utils.ExceptionThrower.authException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genie/v1/user")
public class UserController {

    private final UserService userService;
    private final RateLimitConfig rateLimit;
    private final GenieLogger genie = new GenieLogger(UserController.class);

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/profile/create")
    public ResponseEntity<ApiResponse> createUserProfile(@RequestBody UserProfileRequest request) {

        if (rateLimit.fiveBucket().tryConsume(1)) {
            genie.info("Creating a new user profile");
            return new ResponseEntity<>(userService.createUserProfile(request), HttpStatus.CREATED);
        }

        throw authException("Dear %s, you have exceeded the number of requests allowed per minute. Please try again later.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.TOO_MANY_REQUESTS);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/profile/update")
    public ResponseEntity<ApiResponse> updateUserProfile(@RequestBody ProfileUpdateWrapper request) {

        if (rateLimit.fiveBucket().tryConsume(1)) {
            genie.info("Updating the user profile");

            if (request.getProfileUpdate() != null){
                return new ResponseEntity<>(userService.updateUserProfile(request.getProfileUpdate()), HttpStatus.OK);
            }
            if (request.getProfileDescription() != null) {
                return new ResponseEntity<>(userService.updateUserProfile(request.getProfileDescription()), HttpStatus.OK);
            }
            if (request.getInjury() != null){
                return new ResponseEntity<>(userService.updateUserProfile(request.getInjury()), HttpStatus.OK);
            }
            if (request.getMedicalHistory() != null){
                return new ResponseEntity<>(userService.updateUserProfile(request.getMedicalHistory()), HttpStatus.OK);
            }
            throw authException("Dear %s, you have tried to update the user profile with an invalid request. Please try again later.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.BAD_REQUEST);
        }

        throw authException("Dear %s, you have exceeded the number of requests allowed per minute. Please try again later.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.TOO_MANY_REQUESTS);
    }

}
