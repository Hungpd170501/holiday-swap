package com.example.holidayswap.service.auth;

import com.example.holidayswap.config.security.JwtService;
import com.example.holidayswap.domain.dto.request.auth.LoginRequest;
import com.example.holidayswap.domain.dto.request.auth.RegisterRequest;
import com.example.holidayswap.domain.dto.request.auth.ResetPasswordRequest;
import com.example.holidayswap.domain.dto.response.auth.AuthenticationResponse;
import com.example.holidayswap.domain.entity.auth.*;
import com.example.holidayswap.domain.exception.*;
import com.example.holidayswap.domain.mapper.auth.UserMapper;
import com.example.holidayswap.repository.auth.TokenRepository;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    public static final String USER_NOT_FOUND = "User not found.";
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.access-token.expiration}")
    private long accessTokenExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Transactional
    public void register(RegisterRequest request) {
        userRepository.getUserByEmailEquals(request.getEmail())
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException("Email already exists");
                });
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
                .orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND));
        if (user.getStatus().equals(UserStatus.PENDING)) {
            throw new AccessDeniedException("User not verified.");
        }
        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            throw new AccessDeniedException("User blocked.");
        }
        return getAuthenticationResponse(user);
    }

    private void saveUserToken(User user, String jwtToken, TokenType tokenType, Long expirationTime) {
        var token = Token.builder()
                .user(user)
                .value(jwtToken)
                .tokenType(tokenType)
                .status(TokenStatus.VALID)
                .expirationTime(LocalDateTime.now().plusSeconds(expirationTime / 1000))
                .build();
        tokenRepository.save(token);
    }

    public void confirmRegistration(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND));
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public void forgetPassword(String email) {
        var user = userRepository.getUserByEmailEquals(email)
                .orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND));
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
                    .orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND));

            if (jwtService.isTokenValid(resetPasswordRequest.getToken(), user)) {
                tokenRepository.findByValueEquals(resetPasswordRequest.getToken())
                        .ifPresent(token -> {
                            if (token.getStatus().equals(TokenStatus.VALID)) {
                                token.setStatus(TokenStatus.REVOKED);
                                tokenRepository.save(token);
                                user.setPasswordHash(passwordEncoder.encode(resetPasswordRequest.getPassword()));
                                userRepository.save(user);
                            } else {
                                throw new VerificationException("Invalid token.");
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
                    .orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND));
            if (jwtService.isTokenValid(refreshToken, user)) {
                return getAuthenticationResponse(user);
            }
        }
        throw new VerificationException("Invalid token.");
    }

    private AuthenticationResponse getAuthenticationResponse(User user) {
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserAuthTokens(user);
        saveUserToken(user, accessToken, TokenType.ACCESS, accessTokenExpiration);
        saveUserToken(user, refreshToken, TokenType.REFRESH, refreshExpiration);
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


    public void verifyForgotPasswordToken(String token) {
        String userEmail = jwtService.extractUsername(token);
        if (userEmail != null) {
            var user = this.userRepository.getUserByEmailEquals(userEmail)
                    .orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND));
            if (jwtService.isTokenValid(token, user)) {
                var validUserPasswordResetTokens = tokenRepository.findAllValidTokenByUser(user.getUserId())
                        .stream()
                        .filter(t -> t.getTokenType().equals(TokenType.PASSWORD_RESET))
                        .toList();
                validUserPasswordResetTokens.forEach(t -> {
                    t.setStatus(TokenStatus.REVOKED);
                });
                tokenRepository.saveAll(validUserPasswordResetTokens);
                return;
            }
        }
        throw new VerificationException("Invalid password reset token.");
    }

    public void verifyEmailToken(String token) {
        String userEmail = jwtService.extractUsername(token);
        if (userEmail != null) {
            var user = this.userRepository.getUserByEmailEquals(userEmail)
                    .orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND));
            if (jwtService.isTokenValid(token, user)) {
                var validUserEmailVerificationTokens = tokenRepository.findAllValidTokenByUser(user.getUserId())
                        .stream()
                        .filter(t -> t.getTokenType().equals(TokenType.EMAIL_VERIFICATION))
                        .toList();
                validUserEmailVerificationTokens.forEach(t -> {
                    t.setStatus(TokenStatus.REVOKED);
                });
                tokenRepository.saveAll(validUserEmailVerificationTokens);
                return;
            }
        }
        throw new VerificationException("Invalid email verification token.");
    }
}
