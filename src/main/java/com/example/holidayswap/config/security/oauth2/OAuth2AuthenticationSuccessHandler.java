package com.example.holidayswap.config.security.oauth2;

import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.service.auth.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.holidayswap.constants.ErrorMessage.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserFactory.getOAuth2UserInfo(auth2AuthenticationToken.getAuthorizedClientRegistrationId(), auth2AuthenticationToken.getPrincipal().getAttributes());
        var user = this.userRepository.getUserByEmailEquals(oAuth2UserInfo.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        var authenticationResponse = authenticationService.getAuthenticationResponse(user);
        response.setStatus(HttpStatus.OK.value());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), authenticationResponse);
    }
}
