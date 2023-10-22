package com.genie.gymgenie.controller;

import com.genie.gymgenie.config.RateLimitConfig;
import com.genie.gymgenie.models.payload.user.profile.ChangeEmailRequest;
import com.genie.gymgenie.models.payload.user.profile.ChangePasswordRequest;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.payload.ApiResponse;
import com.genie.gymgenie.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.genie.gymgenie.security.CurrentUser.getCurrentUserDetails;
import static com.genie.gymgenie.utils.ExceptionThrower.TOO_MANY_REQUEST_MSG;
import static com.genie.gymgenie.utils.ExceptionThrower.authException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genie/v1/user")
public class UserController {

    private final UserService userService;
    private final RateLimitConfig rateLimit;
    private final GenieLogger genie = new GenieLogger(UserController.class);

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/password/change")
    public ResponseEntity<ApiResponse> changeUserPassword(@RequestBody ChangePasswordRequest request) {

        if (rateLimit.fiveBucket().tryConsume(1)) {
            genie.info("Updating the user email");
            return new ResponseEntity<>(userService.changeUserPassword(request), HttpStatus.OK);
        }

        throw authException(TOO_MANY_REQUEST_MSG.formatted(getCurrentUserDetails().getUsername()), HttpStatus.TOO_MANY_REQUESTS);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/email/change")
    public ResponseEntity<ApiResponse> changeUserEmail(@RequestBody ChangeEmailRequest request) {

        if (rateLimit.fiveBucket().tryConsume(1)) {
            genie.info("Updating the user email");
            return new ResponseEntity<>(userService.changeUserEmail(request), HttpStatus.OK);
        }

        throw authException(TOO_MANY_REQUEST_MSG.formatted(getCurrentUserDetails().getUsername()), HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/change-email/confirm")
    public ResponseEntity<ApiResponse> verifyChangeUserEmail(
            @Parameter(in = ParameterIn.QUERY, name = "token", description = "The confirmation token received via email.")
            @RequestParam("token") String token,

            @Parameter(in = ParameterIn.QUERY, name = "email", description = "The new (hashed)email address to be associated with the user's account.")
            @RequestParam("e") String email){
        genie.info("Request to verify the change of the user email");
        return new ResponseEntity<>(userService.verifyChangeUserEmail(token, email), HttpStatus.OK);
    }
}
