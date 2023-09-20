package com.example.holidayswap.controller.auth;

import com.example.holidayswap.domain.dto.request.auth.LoginRequest;
import com.example.holidayswap.domain.dto.request.auth.RefreshTokenRequest;
import com.example.holidayswap.domain.dto.request.auth.RegisterRequest;
import com.example.holidayswap.domain.dto.request.auth.ResetPasswordRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.response.auth.AuthenticationResponse;
import com.example.holidayswap.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Validated
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        AuthenticationResponse response = authenticationService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterRequest registerRequest,
                                             @RequestPart List<MultipartFile> propertyImages,
                                             @RequestPart List<MultipartFile> propertyContractImages,
                                             @RequestPart PropertyRegisterRequest propertyRegisterRequest) {
        authenticationService.register(registerRequest, propertyRegisterRequest, propertyImages, propertyContractImages);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/registration/confirm")
    public ResponseEntity<Void> confirmRegistration(@RequestBody Long userId) {
        authenticationService.confirmRegistration(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest.getRefreshToken()));
    }

    @PutMapping("/change-email")
    public ResponseEntity<Void> changeRegistrationEmail(@RequestParam String email) {
        authenticationService.changeEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
        authenticationService.forgetPassword(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        authenticationService.resetPassword(resetPasswordRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        authenticationService.verifyEmailToken(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<Void> verifyForgotPasswordToken(@RequestParam String token) {
        authenticationService.verifyForgotPasswordToken(token);
        return ResponseEntity.ok().build();
    }
}
