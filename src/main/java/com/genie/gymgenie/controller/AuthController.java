package com.genie.gymgenie.controller;

import com.genie.gymgenie.models.payload.user.login.ForgotPasswordRequest;
import com.genie.gymgenie.models.payload.user.login.LoginRequest;
import com.genie.gymgenie.models.payload.user.login.ResetPasswordRequest;
import com.genie.gymgenie.models.payload.user.registration.RegistrationRequest;
import com.genie.gymgenie.models.payload.user.registration.ResendTokenRequest;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.payload.ApiResponse;
import com.genie.gymgenie.service.AuthService;
import com.genie.gymgenie.utils.openapi.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

    @ApiLogin
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Request payload for user login.")
            @RequestBody LoginRequest request){
        genie.info("Login request received");
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

    @ApiForgotPassword
    @PostMapping("/login/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Request payload for resetting a forgotten password")
            @RequestBody ForgotPasswordRequest request) {
        genie.info("Forgot password request received");
        return new ResponseEntity<>(authService.sendForgotPasswordEmail(request), HttpStatus.OK);
    }

    @ApiResetPassword
    @PostMapping("/login/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(
            @Parameter(in = ParameterIn.QUERY, name = "token", description = "Password confirmation token")@RequestParam("token") String token,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Request payload for resetting the user's password.")
            @RequestBody ResetPasswordRequest request) {
        genie.info("Reset password request received");
        return new ResponseEntity<>(authService.resetPassword(token, request), HttpStatus.OK);
    }

    @ApiRefreshToken
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken(HttpServletRequest request){
        genie.info("Request to reset the access token");
        return new ResponseEntity<>(authService.refreshToken(request), HttpStatus.OK);
    }
}
