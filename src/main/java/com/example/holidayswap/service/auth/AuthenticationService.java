package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.request.auth.LoginRequest;
import com.example.holidayswap.domain.dto.request.auth.RegisterRequest;
import com.example.holidayswap.domain.dto.request.auth.ResetPasswordRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.response.auth.AuthenticationResponse;
import com.example.holidayswap.domain.entity.auth.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuthenticationService {
    void register(RegisterRequest request,
                  PropertyRegisterRequest propertyRegisterRequest,
                  List<MultipartFile> propertyImages,
                  List<MultipartFile> propertyContractImages);

    AuthenticationResponse login(LoginRequest loginRequest);

    void confirmRegistration(Long userId);

    void forgetPassword(String email);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    void changeEmail(String email);

    AuthenticationResponse refreshToken(String refreshToken);

    void verifyForgotPasswordToken(String token);

    void verifyEmailToken(String token);

    User getUserFromToken(String token);

    AuthenticationResponse getAuthenticationResponse(User user);
}
