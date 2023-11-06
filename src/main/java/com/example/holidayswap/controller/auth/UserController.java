package com.example.holidayswap.controller.auth;

import com.example.holidayswap.domain.dto.request.auth.UserProfileUpdateRequest;
import com.example.holidayswap.domain.dto.request.auth.UserRequest;
import com.example.holidayswap.domain.dto.request.auth.UserUpdateRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import com.example.holidayswap.service.auth.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Operation(
            description = "Get current user info"
    )
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserInfo() {
        return ResponseEntity.ok(userService.getUserInfo());
    }

    @GetMapping("/search")
    public Page<UserProfileResponse> findAllByEmailNamePhoneWithPagination(
            @RequestParam(name = "email", defaultValue = "") String email,
            @RequestParam(name = "username", defaultValue = "") String name,
            @RequestParam(name = "phone", defaultValue = "") String phone,
            @RequestParam(name = "status", defaultValue = "") Set<UserStatus> statusSet,
            @RequestParam(name = "roleIds", defaultValue = "") Set<Long> roleIds,
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "id") String sortProps,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return userService.findAllByEmailNamePhoneStatusRoleWithPagination(email, name, phone, statusSet, roleIds, limit, offset, sortProps, sortDirection);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> registerUser(@ModelAttribute UserRequest userRequest) throws IOException {
        userService.createUser(userRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Update user status(ACTIVE, BLOCKED, PENDING)"
    )
    @PutMapping("/{userId}/status")
    public ResponseEntity<Void> updateUserStatus(@PathVariable("userId") Long userId, @RequestBody UserStatus status) {
        userService.updateUserStatus(userId, status);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUser(@PathVariable("userId") Long userId, @ModelAttribute UserUpdateRequest userUpdateRequest) throws IOException {
        userService.updateUser(userId, userUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserProfile(@ModelAttribute UserProfileUpdateRequest userUpdateRequest){
        userService.updateUserProfile(userUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
