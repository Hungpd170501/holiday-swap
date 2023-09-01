package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.request.auth.RegisterRequest;
import com.example.holidayswap.domain.dto.request.auth.UserRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;

import java.util.List;

public interface UserService {
    UserProfileResponse getUserById(Long userId);

    UserProfileResponse getUserInfo();

    void deleteUser(Long userId);

    List<UserProfileResponse> findAllByEmailNamePhoneWithPagination(String email, String name, String phone, Integer limit, Integer offset);

    void createUser(UserRequest userRequest);
}
