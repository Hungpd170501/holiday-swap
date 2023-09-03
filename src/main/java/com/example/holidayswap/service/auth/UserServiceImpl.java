package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.request.auth.UserRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.auth.UserMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.PROFILE_NOT_FOUND;
import static com.example.holidayswap.constants.ErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> {
                            throw new EntityNotFoundException(USER_NOT_FOUND);
                        });
    }

    @Override
    public List<UserProfileResponse> findAllByEmailNamePhoneWithPagination(String email, String name, String phone, Integer limit, Integer offset) {
        Page<User> userPage = userRepository.findAllByEmailNamePhoneWithPagination(email, StringUtils.stripAccents(name), phone, PageRequest.of(offset, limit));
        return userPage.stream().map(userMapper::toUserProfileResponse).toList();
    }

    @Override
    public void createUser(UserRequest userRequest) {
        var user = userMapper.toUserEntity(userRequest);
        user.setPasswordHash(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);
    }
}
