package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.entity.auth.TokenStatus;
import com.example.holidayswap.repository.auth.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

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