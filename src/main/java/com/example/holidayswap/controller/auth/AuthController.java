package com.example.holidayswap.controller.auth;

import com.example.holidayswap.domain.dto.request.auth.LoginRequest;
import com.example.holidayswap.domain.dto.request.auth.RefreshTokenRequest;
import com.example.holidayswap.domain.dto.request.auth.RegisterRequest;
import com.example.holidayswap.domain.dto.request.auth.ResetPasswordRequest;
import com.example.holidayswap.domain.dto.response.auth.AuthenticationResponse;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Validated
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;
    private static final String FORGOT_PASSWORD_REDIRECT = "https://holiday-swap.vercel.app/forgot-password";

    @Operation(
            description = "Returns new access and refresh token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Incorrect email or password.", content = @Content),
                    @ApiResponse(responseCode = "403", description = "User not verified. || User is blocked.", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Email not found.", content = @Content)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "email": "hungpd170501@gmail.com",
                                              "password": "password"
                                            }
                                            """
                            )
                    )
            )
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        AuthenticationResponse response = authenticationService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "204", description = "successful operation"),
                    @ApiResponse(responseCode = "409", description = "Email has already been taken."),
                    @ApiResponse(responseCode = "500", description = "Validation exception, constrain violation || Server error.")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "email": "hungpd170501@gmail.com",
                                              "password": "password",
                                              "username": "hung pham",
                                              "gender": "MALE",
                                              "dob": "2001-01-01",
                                              "phone": "0333325363",
                                              "role": {
                                                "roleId": 1
                                              }
                                            }
                                            """
                            )
                    )
            )
    )
    @PostMapping("/register")
    public ResponseEntity<UserProfileResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        var userRes = authenticationService.register(registerRequest);
        return ResponseEntity.ok(userRes);
    }

    @Operation(
            description = "Admin/Staff change user status to ACTIVE"
    )
    @PutMapping("/registration/confirm")
    public ResponseEntity<Void> confirmRegistration(@RequestBody Long userId) {
        authenticationService.confirmRegistration(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            description = "Rotate refresh token to get new access token"
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest.getRefreshToken()));
    }

    @Operation(
            description = "Not implemented yet"
    )
    @PutMapping("/change-email")
    public ResponseEntity<Void> changeRegistrationEmail(@RequestParam String email) {
        authenticationService.changeEmail(email);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Send email to user to reset password"
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
        authenticationService.forgetPassword(email);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Update new password by reset password token"
    )
    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        authenticationService.resetPassword(resetPasswordRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Verify email by token when user click on link in email"
    )
    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        authenticationService.verifyEmailToken(token);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Verify reset password token when user click on link in email"
    )
    @GetMapping("/forgot-password")
    public ResponseEntity<Void> verifyForgotPasswordToken(@RequestParam String token, HttpServletResponse response) throws IOException {
        authenticationService.verifyForgotPasswordToken(token);
        String redirectUrl = UriComponentsBuilder
                .fromUriString(FORGOT_PASSWORD_REDIRECT)
                .queryParam("token", token)
                .queryParam("email", authenticationService.getUserFromToken(token).getEmail())
                .build()
                .toUriString();
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verification-code/google")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam String email) {
        authenticationService.sendVerificationCodeViaGoogle(email);
        return ResponseEntity.ok().build();
    }

    @Operation(
            description = "Update new password by otp"
    )
    @PutMapping("/reset-password/opt")
    public ResponseEntity<Void> resetPasswordByOtp(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        authenticationService.resetPasswordByOtp(resetPasswordRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verify-otp")
    public ResponseEntity<Void> verify(@RequestParam String otp, @RequestParam String email) {
        authenticationService.verifyOtp(otp, email);
        return ResponseEntity.noContent().build();
    }
}
