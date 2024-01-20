package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.request.auth.ChangePasswordRequest;
import com.example.holidayswap.domain.dto.request.auth.UserProfileUpdateRequest;
import com.example.holidayswap.domain.dto.request.auth.UserRequest;
import com.example.holidayswap.domain.dto.request.auth.UserUpdateRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    UserProfileResponse getUserById(Long userId);

    UserProfileResponse getUserInfo();

    Optional<User> getUser();


    void deleteUser(Long userId);

    Page<UserProfileResponse> findAllByEmailNamePhoneStatusRoleWithPagination(String email, String name, String phone, Set<UserStatus> statusSet, Set<Long> roleIds, Integer limit, Integer offset, String sortProps, String sortDirection);

    void createUser(UserRequest userRequest) throws IOException;

    void updateUserStatus(Long userId, UserStatus status);

    void updateUser(Long userId, UserUpdateRequest userUpdateRequest);

    void updateUserProfile(UserProfileUpdateRequest userUpdateRequest);

    void upgradeUserToMember(Long userId);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    String getUserName(Long userId,Long bookingId);
}
