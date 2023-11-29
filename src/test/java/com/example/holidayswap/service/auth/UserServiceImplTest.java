package com.example.holidayswap.service.auth;

import com.amazonaws.AmazonServiceException;
import com.example.holidayswap.domain.dto.request.auth.UserProfileUpdateRequest;
import com.example.holidayswap.domain.dto.request.auth.UserRequest;
import com.example.holidayswap.domain.dto.request.auth.UserUpdateRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.auth.Gender;
import com.example.holidayswap.domain.entity.auth.Role;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.repository.auth.RoleRepository;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.service.FileService;
import com.example.holidayswap.service.payment.IWalletService;
import com.example.holidayswap.utils.AuthUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private FileService mockFileService;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private AuthUtils mockAuthUtils;
    @Mock
    private IWalletService mockWalletService;

    private UserServiceImpl userServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        userServiceImplUnderTest = new UserServiceImpl(mockUserRepository, mockPasswordEncoder, mockFileService,
                mockRoleRepository, mockAuthUtils, mockWalletService);
    }

//    @Test
//    void testGetUserById() {
//        // Setup
//        final UserProfileResponse expectedResult = new UserProfileResponse();
//        expectedResult.setUserId(0L);
//        expectedResult.setEmail("email");
//        expectedResult.setUsername("username");
//        expectedResult.setFullName("fullName");
//        expectedResult.setGender(Gender.MALE);
//
//        // Configure UserRepository.getUserByUserIdEquals(...).
//        final Optional<User> user = Optional.of(User.builder()
//                .userId(0L)
//                .passwordHash("passwordHash")
//                .avatar("avatar")
//                .status(UserStatus.ACTIVE)
//                .role(Role.builder().build())
//                .build());
//        when(mockUserRepository.getUserByUserIdEquals(0L)).thenReturn(user);
//
//        // Run the test
//        final UserProfileResponse result = userServiceImplUnderTest.getUserById(0L);
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
//    }

    @Test
    void testGetUserById_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.getUserByUserIdEquals(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.getUserById(0L)).isInstanceOf(EntityNotFoundException.class);
    }

//    @Test
//    void testGetUserInfo() {
//        // Setup
//        final UserProfileResponse expectedResult = new UserProfileResponse();
//        expectedResult.setUserId(0L);
//        expectedResult.setEmail("email");
//        expectedResult.setUsername("username");
//        expectedResult.setFullName("fullName");
//        expectedResult.setGender(Gender.MALE);
//
//        // Run the test
//        final UserProfileResponse result = userServiceImplUnderTest.getUserInfo();
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
//    }

//    @Test
//    void testGetUser() {
//        // Setup
//        final Optional<User> expectedResult = Optional.of(User.builder()
//                .userId(0L)
//                .passwordHash("passwordHash")
//                .avatar("avatar")
//                .status(UserStatus.ACTIVE)
//                .role(Role.builder().build())
//                .build());
//
//        // Run the test
//        final Optional<User> result = userServiceImplUnderTest.getUser();
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
//    }

    @Test
    void testDeleteUser() {
        // Setup
        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .userId(0L)
                .passwordHash("passwordHash")
                .avatar("avatar")
                .status(UserStatus.ACTIVE)
                .role(Role.builder().build())
                .build());
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        userServiceImplUnderTest.deleteUser(0L);

        // Verify the results
        verify(mockUserRepository).delete(User.builder()
                .userId(0L)
                .passwordHash("passwordHash")
                .avatar("avatar")
                .status(UserStatus.ACTIVE)
                .role(Role.builder().build())
                .build());
    }

    @Test
    void testDeleteUser_UserRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.deleteUser(0L)).isInstanceOf(EntityNotFoundException.class);
    }

//    @Test
//    void testFindAllByEmailNamePhoneStatusRoleWithPagination() {
//        // Setup
//        // Configure UserRepository.findAllByEmailNamePhoneStatusRoleWithPagination(...).
//        final Page<User> users = new PageImpl<>(List.of(User.builder()
//                .userId(0L)
//                .passwordHash("passwordHash")
//                .avatar("avatar")
//                .status(UserStatus.ACTIVE)
//                .role(Role.builder().build())
//                .build()));
//        when(mockUserRepository.findAllByEmailNamePhoneStatusRoleWithPagination("email", "name", "phone",
//                Set.of(UserStatus.ACTIVE), Set.of(0L), PageRequest.of(0, 1))).thenReturn(users);
//
//        // Run the test
//        final Page<UserProfileResponse> result = userServiceImplUnderTest.findAllByEmailNamePhoneStatusRoleWithPagination(
//                "email", "name", "phone", Set.of(UserStatus.ACTIVE), Set.of(0L), 0, 0, "sortProps", "sortDirection");
//
//        // Verify the results
//    }

//    @Test
//    void testFindAllByEmailNamePhoneStatusRoleWithPagination_UserRepositoryReturnsNoItems() {
//        // Setup
//        when(mockUserRepository.findAllByEmailNamePhoneStatusRoleWithPagination("email", "name", "phone",
//                Set.of(UserStatus.ACTIVE), Set.of(0L), PageRequest.of(0, 1)))
//                .thenReturn(new PageImpl<>(Collections.emptyList()));
//
//        // Run the test
//        final Page<UserProfileResponse> result = userServiceImplUnderTest.findAllByEmailNamePhoneStatusRoleWithPagination(
//                "email", "name", "phone", Set.of(UserStatus.ACTIVE), Set.of(0L), 0, 0, "sortProps", "sortDirection");
//
//        // Verify the results
//    }

//    @Test
//    void testCreateUser() throws Exception {
//        // Setup
//        final UserRequest userRequest = new UserRequest();
//        userRequest.setAvatar(new MockMultipartFile("name", "content".getBytes()));
//        userRequest.setEmail("email");
//        userRequest.setPassword("password");
//        userRequest.setUsername("username");
//        userRequest.setRoleId(0L);
//
//        when(mockRoleRepository.findById(0L)).thenReturn(Optional.of(Role.builder().build()));
//        when(mockPasswordEncoder.encode("password")).thenReturn("passwordHash");
//        when(mockFileService.uploadFile(any(MultipartFile.class))).thenReturn("avatar");
//
//        // Run the test
//        userServiceImplUnderTest.createUser(userRequest);
//
//        // Verify the results
//        verify(mockUserRepository).save(User.builder()
//                .userId(0L)
//                .passwordHash("passwordHash")
//                .avatar("avatar")
//                .status(UserStatus.ACTIVE)
//                .role(Role.builder().build())
//                .build());
//        verify(mockWalletService).CreateWallet(0L);
//    }

    @Test
    void testCreateUser_RoleRepositoryReturnsAbsent() {
        // Setup
        final UserRequest userRequest = new UserRequest();
        userRequest.setAvatar(new MockMultipartFile("name", "content".getBytes()));
        userRequest.setEmail("email");
        userRequest.setPassword("password");
        userRequest.setUsername("username");
        userRequest.setRoleId(0L);

        when(mockRoleRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.createUser(userRequest))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testCreateUser_FileServiceThrowsIOException() throws Exception {
        // Setup
        final UserRequest userRequest = new UserRequest();
        userRequest.setAvatar(new MockMultipartFile("name", "content".getBytes()));
        userRequest.setEmail("email");
        userRequest.setPassword("password");
        userRequest.setUsername("username");
        userRequest.setRoleId(0L);

        when(mockRoleRepository.findById(0L)).thenReturn(Optional.of(Role.builder().build()));
        when(mockPasswordEncoder.encode("password")).thenReturn("passwordHash");
        when(mockFileService.uploadFile(any(MultipartFile.class))).thenThrow(IOException.class);

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.createUser(userRequest)).isInstanceOf(IOException.class);
    }

    @Test
    void testUpdateUserStatus() {
        // Setup
        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .userId(0L)
                .passwordHash("passwordHash")
                .avatar("avatar")
                .status(UserStatus.ACTIVE)
                .role(Role.builder().build())
                .build());
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        userServiceImplUnderTest.updateUserStatus(0L, UserStatus.ACTIVE);

        // Verify the results
        verify(mockUserRepository).save(User.builder()
                .userId(0L)
                .passwordHash("passwordHash")
                .avatar("avatar")
                .status(UserStatus.ACTIVE)
                .role(Role.builder().build())
                .build());
    }

    @Test
    void testUpdateUserStatus_UserRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.updateUserStatus(0L, UserStatus.ACTIVE))
                .isInstanceOf(EntityNotFoundException.class);
    }

//    @Test
//    void testUpdateUser() throws Exception {
//        // Setup
//        final UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
//        userUpdateRequest.setAvatar(new MockMultipartFile("name", "content".getBytes()));
//        userUpdateRequest.setEmail("email");
//        userUpdateRequest.setFullName("fullName");
//        userUpdateRequest.setGender(Gender.MALE);
//        userUpdateRequest.setRoleId(0L);
//
//        when(mockRoleRepository.findById(0L)).thenReturn(Optional.of(Role.builder().build()));
//
//        // Configure UserRepository.findById(...).
//        final Optional<User> user = Optional.of(User.builder()
//                .userId(0L)
//                .passwordHash("passwordHash")
//                .avatar("avatar")
//                .status(UserStatus.ACTIVE)
//                .role(Role.builder().build())
//                .build());
//        when(mockUserRepository.findById(0L)).thenReturn(user);
//
//        when(mockFileService.uploadFile(any(MultipartFile.class))).thenReturn("avatar");
//
//        // Run the test
//        userServiceImplUnderTest.updateUser(0L, userUpdateRequest);
//
//        // Verify the results
//        verify(mockUserRepository).save(User.builder()
//                .userId(0L)
//                .passwordHash("passwordHash")
//                .avatar("avatar")
//                .status(UserStatus.ACTIVE)
//                .role(Role.builder().build())
//                .build());
//    }

    @Test
    void testUpdateUser_RoleRepositoryReturnsAbsent() {
        // Setup
        final UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setAvatar(new MockMultipartFile("name", "content".getBytes()));
        userUpdateRequest.setEmail("email");
        userUpdateRequest.setFullName("fullName");
        userUpdateRequest.setGender(Gender.MALE);
        userUpdateRequest.setRoleId(0L);

        when(mockRoleRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.updateUser(0L, userUpdateRequest))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testUpdateUser_UserRepositoryFindByIdReturnsAbsent() {
        // Setup
        final UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setAvatar(new MockMultipartFile("name", "content".getBytes()));
        userUpdateRequest.setEmail("email");
        userUpdateRequest.setFullName("fullName");
        userUpdateRequest.setGender(Gender.MALE);
        userUpdateRequest.setRoleId(0L);

        when(mockRoleRepository.findById(0L)).thenReturn(Optional.of(Role.builder().build()));
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.updateUser(0L, userUpdateRequest))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testUpdateUser_FileServiceThrowsIOException() throws Exception {
        // Setup
        final UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setAvatar(new MockMultipartFile("name", "content".getBytes()));
        userUpdateRequest.setEmail("email");
        userUpdateRequest.setFullName("fullName");
        userUpdateRequest.setGender(Gender.MALE);
        userUpdateRequest.setRoleId(0L);

        when(mockRoleRepository.findById(0L)).thenReturn(Optional.of(Role.builder().build()));

        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .userId(0L)
                .passwordHash("passwordHash")
                .avatar("avatar")
                .status(UserStatus.ACTIVE)
                .role(Role.builder().build())
                .build());
        when(mockUserRepository.findById(0L)).thenReturn(user);

        when(mockFileService.uploadFile(any(MultipartFile.class))).thenThrow(IOException.class);

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.updateUser(0L, userUpdateRequest))
                .isInstanceOf(AmazonServiceException.class);
    }

//    @Test
//    void testUpdateUserProfile() throws Exception {
//        // Setup
//        final UserProfileUpdateRequest userProfileUpdateRequest = new UserProfileUpdateRequest();
//        userProfileUpdateRequest.setAvatar(new MockMultipartFile("name", "content".getBytes()));
//        userProfileUpdateRequest.setFullName("fullName");
//        userProfileUpdateRequest.setGender(Gender.MALE);
//        userProfileUpdateRequest.setDob(LocalDate.of(2020, 1, 1));
//
//        // Configure AuthUtils.getAuthenticatedUser(...).
//        final User user = User.builder()
//                .userId(0L)
//                .passwordHash("passwordHash")
//                .avatar("avatar")
//                .status(UserStatus.ACTIVE)
//                .role(Role.builder().build())
//                .build();
//        when(mockAuthUtils.getAuthenticatedUser()).thenReturn(user);
//
//        when(mockFileService.uploadFile(any(MultipartFile.class))).thenReturn("avatar");
//
//        // Run the test
//        userServiceImplUnderTest.updateUserProfile(userProfileUpdateRequest);
//
//        // Verify the results
//        verify(mockUserRepository).save(User.builder()
//                .userId(0L)
//                .passwordHash("passwordHash")
//                .avatar("avatar")
//                .status(UserStatus.ACTIVE)
//                .role(Role.builder().build())
//                .build());
//    }

    @Test
    void testUpdateUserProfile_FileServiceThrowsIOException() throws Exception {
        // Setup
        final UserProfileUpdateRequest userProfileUpdateRequest = new UserProfileUpdateRequest();
        userProfileUpdateRequest.setAvatar(new MockMultipartFile("name", "content".getBytes()));
        userProfileUpdateRequest.setFullName("fullName");
        userProfileUpdateRequest.setGender(Gender.MALE);
        userProfileUpdateRequest.setDob(LocalDate.of(2020, 1, 1));

        // Configure AuthUtils.getAuthenticatedUser(...).
        final User user = User.builder()
                .userId(0L)
                .passwordHash("passwordHash")
                .avatar("avatar")
                .status(UserStatus.ACTIVE)
                .role(Role.builder().build())
                .build();
        when(mockAuthUtils.getAuthenticatedUser()).thenReturn(user);

        when(mockFileService.uploadFile(any(MultipartFile.class))).thenThrow(IOException.class);

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.updateUserProfile(userProfileUpdateRequest))
                .isInstanceOf(AmazonServiceException.class);
    }
}
