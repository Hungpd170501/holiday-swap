package com.example.holidayswap.config.security.oauth2;

import com.example.holidayswap.domain.entity.auth.UserStatus;
import com.example.holidayswap.domain.exception.AccessDeniedException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.exception.OAuth2AuthenticationProcessingException;
import com.example.holidayswap.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        var oAuth2UserInfo = OAuth2UserFactory.getOAuth2UserInfo(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        var user = userRepository.getUserByEmailEquals(oAuth2UserInfo.getEmail())
                .orElseThrow(() -> new OAuth2AuthenticationProcessingException("Looks like you're not signed up. Please sign up to continue."));
        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            throw new AccessDeniedException(USER_BLOCKED);
        }
        if (user.getStatus().equals(UserStatus.PENDING)) {
            throw new AccessDeniedException(USER_NOT_VERIFIED);
        }
        return oAuth2User;
    }
}
