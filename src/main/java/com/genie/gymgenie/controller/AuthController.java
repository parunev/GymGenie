package com.genie.gymgenie.controller;

import com.genie.gymgenie.models.payload.user.registration.RegistrationRequest;
import com.genie.gymgenie.models.payload.user.registration.ResendTokenRequest;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.payload.ApiResponse;
import com.genie.gymgenie.service.AuthService;
import com.genie.gymgenie.utils.openapi.ApiRegistration;
import com.genie.gymgenie.utils.openapi.ApiRegistrationConfirm;
import com.genie.gymgenie.utils.openapi.ApiRegistrationResend;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genie/v1/auth")
@Tag(name = "Authentication Controller", description = "API endpoints for user registration, login, and related operations")
public class AuthController {

    private final AuthService authService;
    private final GenieLogger genie = new GenieLogger(AuthController.class);

    @ApiRegistration
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Request payload for user registration")
            @RequestBody RegistrationRequest request) {
        genie.info("Registration request received");
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @ApiRegistrationConfirm
    @GetMapping("/register/confirm")
    public ResponseEntity<ApiResponse> confirmRegister(
            @Parameter(in = ParameterIn.QUERY, name = "token", description = "Email confirmation token") @RequestParam("token") String token){
        genie.info("Email confirmation request received");
        return new ResponseEntity<>(authService.confirmRegistration(token), HttpStatus.OK);
    }

    @ApiRegistrationResend
    @PostMapping("/register/resend-token")
    public ResponseEntity<ApiResponse> resendConfirmationToken(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Request payload for resending a confirmation token")
            @RequestBody ResendTokenRequest request) {
        genie.info("Resend token request received");
        return new ResponseEntity<>(authService.resendConfirmation(request), HttpStatus.OK);
    }

}
