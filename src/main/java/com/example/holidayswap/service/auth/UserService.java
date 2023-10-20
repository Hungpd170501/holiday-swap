package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.request.auth.UserRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    UserProfileResponse getUserById(Long userId);

    UserProfileResponse getUserInfo();

    Optional<User> getUser();


    void deleteUser(Long userId);

    Page<UserProfileResponse> findAllByEmailNamePhoneStatusRoleWithPagination(String email, String name, String phone, Set<UserStatus> statusSet, Set<Long> roleIds, Integer limit, Integer offset, String sortProps, String sortDirection);

    void createUser(UserRequest userRequest);

    void updateUserStatus(Long userId, UserStatus status);
}
