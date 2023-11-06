package com.example.holidayswap.service.auth;

import com.amazonaws.AmazonServiceException;
import com.example.holidayswap.domain.dto.request.auth.UserProfileUpdateRequest;
import com.example.holidayswap.domain.dto.request.auth.UserRequest;
import com.example.holidayswap.domain.dto.request.auth.UserUpdateRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.auth.UserMapper;
import com.example.holidayswap.repository.auth.RoleRepository;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.service.FileService;
import com.example.holidayswap.service.payment.IWalletService;
import com.example.holidayswap.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final RoleRepository roleRepository;
    private final AuthUtils authUtils;

    private final IWalletService walletService;

    @Override
    public UserProfileResponse getUserById(Long userId) {
        return userRepository.getUserByUserIdEquals(userId).map(UserMapper.INSTANCE::toUserProfileResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public UserProfileResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User user) {
                return UserMapper.INSTANCE.toUserProfileResponse(user);
            }
        }
        throw new EntityNotFoundException(PROFILE_NOT_FOUND);
    }

    @Override
    public Optional<User> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User user) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> {
                            throw new EntityNotFoundException(USER_NOT_FOUND);
                        });
    }

    @Override
    public Page<UserProfileResponse> findAllByEmailNamePhoneStatusRoleWithPagination(String email, String name, String phone, Set<UserStatus> statusSet, Set<Long> roleIds, Integer limit, Integer offset, String sortProps, String sortDirection) {
        return userRepository.findAllByEmailNamePhoneStatusRoleWithPagination(
                        email, StringUtils.stripAccents(name).toUpperCase(), phone, statusSet, roleIds,
                        PageRequest.of(offset, limit, Sort.by(Sort.Direction.fromString(sortDirection), sortProps)))
                .map(UserMapper.INSTANCE::toUserProfileResponse);
    }

    @Override
    public void createUser(UserRequest userRequest) throws IOException {
        var role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));
        var user = UserMapper.INSTANCE.toUserEntity(userRequest);
        user.setPasswordHash(passwordEncoder.encode(userRequest.getPassword()));
        if (userRequest.getAvatar() != null) {
            user.setAvatar(fileService.uploadFile(userRequest.getAvatar()));
        }
        user.setRole(role);
        userRepository.save(user);
        walletService.CreateWallet(user.getUserId());
    }

    @Override
    public void updateUserStatus(Long userId, UserStatus status) {
        userRepository.findById(userId).ifPresentOrElse(user -> {
            user.setStatus(status);
            userRepository.save(user);
        }, () -> {
            throw new EntityNotFoundException(USER_NOT_FOUND);
        });
    }

    @Override
    public void updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        var role = roleRepository.findById(userUpdateRequest.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));
        userRepository.findById(userId).ifPresentOrElse(user -> {
            UserMapper.INSTANCE.toUserEntity(user, userUpdateRequest);
            if (userUpdateRequest.getAvatar() != null) {
                try {
                    user.setAvatar(fileService.uploadFile(userUpdateRequest.getAvatar()));
                } catch (IOException e) {
                    throw new AmazonServiceException("Error while uploading avatar");
                }
            }
            user.setRole(role);
            userRepository.save(user);
        }, () -> {
            throw new EntityNotFoundException(USER_NOT_FOUND);
        });
    }

    @Override
    public void updateUserProfile(UserProfileUpdateRequest userProfileUpdateRequest) {
        var user = authUtils.getAuthenticatedUser();
        UserMapper.INSTANCE.toUserEntity(user, userProfileUpdateRequest);
        if (userProfileUpdateRequest.getAvatar() != null) {
            try {
                user.setAvatar(fileService.uploadFile(userProfileUpdateRequest.getAvatar()));
            } catch (IOException e) {
                throw new AmazonServiceException("Error while uploading avatar");
            }
        }
        userRepository.save(user);
    }
}
