package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.request.auth.LoginRequest;
import com.example.holidayswap.domain.dto.request.auth.RegisterRequest;
import com.example.holidayswap.domain.dto.request.auth.ResetPasswordRequest;
import com.example.holidayswap.domain.entity.auth.Token;
import com.example.holidayswap.domain.entity.auth.TokenType;
import com.example.holidayswap.domain.mapper.auth.UserMapper;
import com.example.holidayswap.repository.auth.TokenRepository;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    public void register(RegisterRequest request) {
        var user = UserMapper.INSTANCE.toUserEntity(request);
        user.setStatus(2);
        userRepository.save(user);
        emailService.sendRegistrationReceipt(user.getEmail(), user.getUsername());
        var token = tokenRepository.save(Token
                .builder()
                .user(user)
                .tokenType(TokenType.EMAIL_VERIFICATION)
                .expirationTime(LocalDateTime.now().plusHours(24))
                .status(1)
                .value(generateRandomTokenValue()).build());
        emailService.sendVerificationEmail(user.getEmail(), token.getValue());
    }

    public void login(LoginRequest loginRequest) {
    }

    public void confirmRegistration(Long userId) {
    }

    public void forgetPassword(String email) {
    }

    public void resetPassword(ResetPasswordRequest resetPassword) {
    }

    public void changeEmail(String email) {
    }

    public void refreshToken(String refreshToken) {
    }

    public String generateRandomTokenValue() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[32]; // You can adjust the token length as needed
        random.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
