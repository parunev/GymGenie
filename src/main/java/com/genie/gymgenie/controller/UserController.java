package com.genie.gymgenie.controller;

import com.genie.gymgenie.models.payload.user.profile.ChangeEmailRequest;
import com.genie.gymgenie.models.payload.user.profile.ChangePasswordRequest;
import com.genie.gymgenie.security.payload.ApiResponse;
import com.genie.gymgenie.service.UserService;
import com.genie.gymgenie.utils.RateLimitExecutor;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genie/v1/user")
public class UserController {

    private final UserService userService;
    private final RateLimitExecutor executor;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/password/change")
    public ResponseEntity<ApiResponse> changeUserPassword(@RequestBody ChangePasswordRequest request) {
        return new ResponseEntity<>(executor.executeUser(() -> userService.changeUserPassword(request),
                "Change password"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/email/change")
    public ResponseEntity<ApiResponse> changeUserEmail(@RequestBody ChangeEmailRequest request) {
        return new ResponseEntity<>(executor.executeUser(() -> userService.changeUserEmail(request),
                "Change email"), HttpStatus.OK);
    }

    @GetMapping("/change-email/confirm")
    public ResponseEntity<ApiResponse> verifyChangeUserEmail(
            @Parameter(in = ParameterIn.QUERY, name = "token", description = "The confirmation token received via email.")
            @RequestParam("token") String token,

            @Parameter(in = ParameterIn.QUERY, name = "email", description = "The new (hashed)email address to be associated with the user's account.")
            @RequestParam("e") String email){
        return new ResponseEntity<>(executor.executeUser(() -> userService.verifyChangeUserEmail(token, email),
                "Verify change email"), HttpStatus.OK);
    }
}
