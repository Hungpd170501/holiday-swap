package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.exception.ApiError;
import com.example.holidayswap.domain.mapper.auth.UserMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserProfileResponse getUserById(Long userId) {
        return userRepository.getUserByUserIdEquals(userId).map(UserMapper.INSTANCE::toUserProfileResponse)
                .orElseThrow(() -> ApiError.builder().status(HttpStatus.NOT_FOUND).message("User not found").build());
    }
}
