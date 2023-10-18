package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.request.auth.UserRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.auth.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserProfileResponse getUserById(Long userId);

    UserProfileResponse getUserInfo();

    Optional<User> getUser();


    void deleteUser(Long userId);

    List<UserProfileResponse> findAllByEmailNamePhoneWithPagination(String email, String name, String phone, Integer limit, Integer offset);

    void createUser(UserRequest userRequest);
}
