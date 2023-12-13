package com.example.holidayswap.service.auth;

import com.example.holidayswap.config.security.JwtService;
import com.example.holidayswap.domain.dto.request.auth.LoginRequest;
import com.example.holidayswap.domain.dto.request.auth.RegisterRequest;
import com.example.holidayswap.domain.dto.request.auth.ResetPasswordRequest;
import com.example.holidayswap.domain.dto.response.auth.AuthenticationResponse;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.auth.*;
import com.example.holidayswap.domain.exception.AccessDeniedException;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.exception.VerificationException;
import com.example.holidayswap.domain.mapper.auth.UserMapper;
import com.example.holidayswap.repository.auth.RoleRepository;
import com.example.holidayswap.repository.auth.TokenRepository;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.service.EmailService;
import com.example.holidayswap.service.payment.IWalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    public static final int OPT_EXP_MINUTES = 30;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    private IWalletService walletService;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.access-token.expiration}")
    private long accessTokenExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     TokenRepository tokenRepository,
                                     RoleRepository roleRepository,
                                     EmailService emailService,
                                     JwtService jwtService,
                                     PasswordEncoder passwordEncoder,
                                     @Lazy
                                     AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserProfileResponse register(RegisterRequest request) {
        var role = roleRepository.findById(request.getRole().getRoleId())
                .orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));
        userRepository.getUserByEmailEquals(request.getEmail())
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException(EMAIL_HAS_ALREADY_BEEN_TAKEN);
                });
        var user = UserMapper.INSTANCE.toUserEntity(request);
        user.setStatus(UserStatus.PENDING);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        var userRes = userRepository.save(user);
        walletService.CreateWallet(user.getUserId());
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
        return UserMapper.INSTANCE.toUserProfileResponse(userRes);
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.getUserByEmailEquals(loginRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(EMAIL_NOT_FOUND));
        if (user.getStatus().equals(UserStatus.PENDING)) {
            throw new AccessDeniedException(USER_NOT_VERIFIED);
        }
        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            throw new AccessDeniedException(USER_BLOCKED);
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

    @Override
    public void confirmRegistration(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(u -> {
                    u.setStatus(UserStatus.ACTIVE);
                    userRepository.save(u);
                }, () -> {
                    throw new EntityNotFoundException(USER_NOT_FOUND);
                });
    }

    @Override
    public void forgetPassword(String email) {
        var user = userRepository.getUserByEmailEquals(email)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
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

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String userEmail = jwtService.extractUsername(resetPasswordRequest.getToken());
        if (userEmail == null) {
            throw new VerificationException(USER_NOT_FOUND);
        }
        var user = this.userRepository.getUserByEmailEquals(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        if (!jwtService.isTokenValid(resetPasswordRequest.getToken(), user)) {
            throw new VerificationException(PASSWORD_RESET_TOKEN_INVALID);
        }
        tokenRepository.findByValueEquals(resetPasswordRequest.getToken())
                .ifPresent(token -> {
                    if (token.getStatus().equals(TokenStatus.VALID)) {
                        changePasswordAndRevokeTokens(resetPasswordRequest, user, token);
                    } else if (token.getStatus().equals(TokenStatus.REVOKED)) {
                        throw new VerificationException(TOKEN_REVOKED);
                    } else if (token.getStatus().equals(TokenStatus.EXPIRED)) {
                        throw new VerificationException(TOKEN_EXPIRED);
                    } else {
                        throw new VerificationException(JWT_TOKEN_INVALID);
                    }
                });
    }

    @Override
    public void changeEmail(String email) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AuthenticationResponse refreshToken(String refreshToken) {
        String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new VerificationException(JWT_TOKEN_INVALID);
        }
        var user = this.userRepository.getUserByEmailEquals(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        var isTokenValid = tokenRepository.findByValueEquals(refreshToken)
                .map(t -> t.getStatus().equals(TokenStatus.VALID) && t.getExpirationTime().isAfter(java.time.LocalDateTime.now()))
                .orElse(false);
        if (jwtService.isTokenValid(refreshToken, user) && Boolean.TRUE.equals(isTokenValid)) {
            return getAuthenticationResponse(user);
        }
        throw new VerificationException(JWT_TOKEN_INVALID);
    }

    public AuthenticationResponse getAuthenticationResponse(User user) {
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

    private String getRandomNumber() {
        int number = new SecureRandom().nextInt(999999);
        return String.format("%06d", number);
    }

    @Override
    public void sendVerificationCodeViaGoogle(String email) {
        var user = userRepository.getUserByEmailEquals(email)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        var token = tokenRepository.save(Token
                .builder()
                .user(user)
                .tokenType(TokenType.OTP)
                .expirationTime(LocalDateTime.now().plusMinutes(OPT_EXP_MINUTES))
                .status(TokenStatus.VALID)
                .value(getRandomNumber())
                .build());
        emailService.sendVerificationCode(user.getEmail(), user.getUsername(), token.getValue());
    }

    @Override
    public void resetPasswordByOtp(ResetPasswordRequest resetPasswordRequest) {
        var user = this.userRepository.getUserByEmailEquals(resetPasswordRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        tokenRepository.findByValueEqualsAndAndUserIdEqualsAndTypeEqualsOPT(resetPasswordRequest.getEmail(), resetPasswordRequest.getToken())
                .ifPresentOrElse(token -> changePasswordAndRevokeTokens(resetPasswordRequest, user, token),
                        () -> {
                            throw new VerificationException(PASSWORD_RESET_TOKEN_INVALID);
                        }
                );
    }

    private void changePasswordAndRevokeTokens(ResetPasswordRequest resetPasswordRequest, User user, Token token) {
        token.setStatus(TokenStatus.REVOKED);
        tokenRepository.save(token);
        user.setPasswordHash(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        userRepository.save(user);
        CompletableFuture.runAsync(() -> {
            var validUserPasswordResetTokens = tokenRepository.findAllValidTokenByUser(user.getUserId())
                    .stream()
                    .filter(t -> t.getTokenType().equals(TokenType.PASSWORD_RESET))
                    .map(t -> {
                        t.setStatus(TokenStatus.REVOKED);
                        return t;
                    })
                    .toList();
            tokenRepository.saveAll(validUserPasswordResetTokens);
        });
    }

    private void revokeAllUserAuthTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId())
                .stream()
                .filter(t -> t.getTokenType().equals(TokenType.ACCESS) || t.getTokenType().equals(TokenType.REFRESH))
                .toList();
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> token.setStatus(TokenStatus.REVOKED));
            tokenRepository.saveAll(validUserTokens);
        }
    }

    @Override
    public void verifyForgotPasswordToken(String token) {
        getUserFromToken(token);
    }

    @Override
    public void verifyEmailToken(String token) {
        var user = getUserFromToken(token);
        CompletableFuture.runAsync(() -> {
            var validUserEmailVerificationTokens = tokenRepository.findAllValidTokenByUser(user.getUserId())
                    .stream()
                    .filter(t -> t.getTokenType().equals(TokenType.EMAIL_VERIFICATION))
                    .toList();
            validUserEmailVerificationTokens.forEach(t -> t.setStatus(TokenStatus.REVOKED));
            tokenRepository.saveAll(validUserEmailVerificationTokens);
        });
        if (user.getStatus().equals(UserStatus.PENDING)) {
            user.setStatus(UserStatus.ACTIVE);
        }
        user.setEmailVerified(true);
        userRepository.save(user);
    }

    @Override
    public User getUserFromToken(String token) {
        String userEmail = jwtService.extractUsername(token);
        if (userEmail == null) {
            throw new VerificationException(VERIFICATION_TOKEN_INVALID);
        }
        var user = this.userRepository.getUserByEmailEquals(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        if (!jwtService.isTokenValid(token, user)) {
            throw new VerificationException(VERIFICATION_TOKEN_INVALID);
        }
        return user;
    }
}
