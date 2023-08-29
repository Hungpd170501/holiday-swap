package com.example.holidayswap.service.auth;

import com.example.holidayswap.config.security.JwtService;
import com.example.holidayswap.domain.dto.request.auth.LoginRequest;
import com.example.holidayswap.domain.dto.request.auth.RegisterRequest;
import com.example.holidayswap.domain.dto.request.auth.ResetPasswordRequest;
import com.example.holidayswap.domain.dto.response.auth.AuthenticationResponse;
import com.example.holidayswap.domain.entity.auth.*;
import com.example.holidayswap.domain.exception.ApiError;
import com.example.holidayswap.domain.mapper.auth.UserMapper;
import com.example.holidayswap.repository.auth.TokenRepository;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService implements LogoutHandler {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Transactional
    public void register(RegisterRequest request) {
        var user = UserMapper.INSTANCE.toUserEntity(request);
        user.setStatus(UserStatus.PENDING);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        try {
            emailService.sendRegistrationReceipt(user.getEmail(), user.getUsername());
            var token = tokenRepository.save(Token
                    .builder()
                    .user(user)
                    .tokenType(TokenType.EMAIL_VERIFICATION)
                    .expirationTime(LocalDateTime.now().plusSeconds(jwtExpiration / 1000))
                    .status(TokenStatus.VALID)
                    .value(jwtService.generateToken(new HashMap<>(), user))
                    .build());
            emailService.sendVerificationEmail(user.getEmail(), token.getValue());
        } catch (Exception e) {
            log.error("Error sending verification email", e);
        }
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.getUserByEmailEquals(loginRequest.getEmail())
                .orElseThrow(() -> ApiError.builder().status(HttpStatus.NOT_FOUND).message("User not found").build());
        if (user.getStatus().equals(UserStatus.PENDING)) {
            throw ApiError.builder().status(HttpStatus.BAD_REQUEST).message("User not verified!").build();
        }
        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            throw ApiError.builder().status(HttpStatus.BAD_REQUEST).message("User blocked!").build();
        }
        return getAuthenticationResponse(user);
    }

    private void saveUserToken(User user, String jwtToken, TokenType tokenType) {
        var token = Token.builder()
                .user(user)
                .value(jwtToken)
                .tokenType(tokenType)
                .status(TokenStatus.VALID)
                .build();
        tokenRepository.save(token);
    }

    public void confirmRegistration(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> ApiError.builder().status(HttpStatus.NOT_FOUND).message("User not found").build());
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public void forgetPassword(String email) {
        var user = userRepository.getUserByEmailEquals(email)
                .orElseThrow(() -> ApiError.builder().status(HttpStatus.NOT_FOUND).message("User not found").build());
        var token = tokenRepository.save(Token
                .builder()
                .user(user)
                .tokenType(TokenType.EMAIL_VERIFICATION)
                .expirationTime(LocalDateTime.now().plusSeconds(jwtExpiration / 1000))
                .status(TokenStatus.VALID)
                .value(jwtService.generateToken(new HashMap<>(), user))
                .build());
        emailService.sendForgotPasswordEmail(user.getEmail(), token.getValue());
    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String userEmail = jwtService.extractUsername(resetPasswordRequest.getToken());
        if (userEmail != null) {
            var user = this.userRepository.getUserByEmailEquals(userEmail)
                    .orElseThrow(() -> ApiError.builder().status(HttpStatus.NOT_FOUND).message("User not found").build());

            if (jwtService.isTokenValid(resetPasswordRequest.getToken(), user)) {
                tokenRepository.findByValueEquals(resetPasswordRequest.getToken())
                        .ifPresent(token -> {
                            if (token.getStatus().equals(TokenStatus.VALID)) {
                                token.setStatus(TokenStatus.REVOKED);
                                tokenRepository.save(token);
                                user.setPasswordHash(passwordEncoder.encode(resetPasswordRequest.getPassword()));
                                userRepository.save(user);
                            } else {
                                throw ApiError.builder().status(HttpStatus.BAD_REQUEST).message("Invalid token").build();
                            }
                        });
            }
        }
    }

    public void changeEmail(String email) {
    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.getUserByEmailEquals(userEmail)
                    .orElseThrow(() -> ApiError.builder().status(HttpStatus.NOT_FOUND).message("User not found").build());
            if (jwtService.isTokenValid(refreshToken, user)) {
                return getAuthenticationResponse(user);
            }
        }
        throw ApiError.builder().status(HttpStatus.BAD_REQUEST).message("Invalid token").build();
    }

    private AuthenticationResponse getAuthenticationResponse(User user) {
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserAuthTokens(user);
        saveUserToken(user, accessToken, TokenType.ACCESS);
        saveUserToken(user, refreshToken, TokenType.REFRESH);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserAuthTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId())
                .stream()
                .filter(t -> t.getTokenType().equals(TokenType.ACCESS) || t.getTokenType().equals(TokenType.REFRESH))
                .toList();
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setStatus(TokenStatus.REVOKED);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByValueEquals(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setStatus(TokenStatus.REVOKED);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
