package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.request.auth.RoleRequest;
import com.example.holidayswap.domain.dto.response.auth.RoleResponse;
import com.example.holidayswap.domain.entity.auth.Role;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.repository.auth.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository mockRoleRepository;

    private RoleServiceImpl roleServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        roleServiceImplUnderTest = new RoleServiceImpl(mockRoleRepository);
    }

    @Test
    void testGetRoles() {
        // Setup
        final List<RoleResponse> expectedResult = List.of(new RoleResponse(0L, "name", true));

        // Configure RoleRepository.findAll(...).
        final List<Role> roles = List.of(Role.builder()
                .roleId(0L)
                .name("name")
                .status(true)
                .build());
        when(mockRoleRepository.findAll()).thenReturn(roles);

        // Run the test
        final List<RoleResponse> result = roleServiceImplUnderTest.getRoles();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetRoles_RoleRepositoryReturnsNoItems() {
        // Setup
        when(mockRoleRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<RoleResponse> result = roleServiceImplUnderTest.getRoles();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetRoleById() {
        // Setup
        final RoleResponse expectedResult = new RoleResponse(0L, "name", true);

        // Configure RoleRepository.findById(...).
        final Optional<Role> role = Optional.of(Role.builder()
                .roleId(0L)
                .name("name")
                .status(true)
                .build());
        when(mockRoleRepository.findById(0L)).thenReturn(role);

        // Run the test
        final RoleResponse result = roleServiceImplUnderTest.getRoleById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetRoleById_RoleRepositoryReturnsAbsent() {
        // Setup
        when(mockRoleRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> roleServiceImplUnderTest.getRoleById(0L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testCreateRole_ThrowsDataIntegrityViolationException() {
        // Setup
        final RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("name");
        roleRequest.setStatus(true);

        // Configure RoleRepository.findByNameEquals(...).
        final Optional<Role> role = Optional.of(Role.builder()
                .roleId(0L)
                .name("name")
                .status(true)
                .build());
        when(mockRoleRepository.findByNameEquals("name")).thenReturn(role);

        // Run the test
        assertThatThrownBy(() -> roleServiceImplUnderTest.createRole(roleRequest))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void testCreateRole_RoleRepositoryFindByNameEqualsReturnsAbsent() {
        // Setup
        final RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("name");
        roleRequest.setStatus(true);

        final RoleResponse expectedResult = new RoleResponse(0L, "name", true);

        // Stubbing for findByNameEquals
        when(mockRoleRepository.findByNameEquals("name")).thenReturn(Optional.empty());

        when(mockRoleRepository.save(
                ArgumentMatchers.argThat(role -> role.getRoleId() == null && "name".equals(role.getName()) && role.isStatus() && role.getCreatedOn() == null && role.getUsers() == null)
        )).thenAnswer(invocation -> {
            Role roleToSave = invocation.getArgument(0);
            roleToSave.setRoleId(0L);
            return roleToSave;
        });

        // Run the test
        final RoleResponse result = roleServiceImplUnderTest.createRole(roleRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateRole() {
        // Setup
        final RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("name");
        roleRequest.setStatus(false);

        final RoleResponse expectedResult = new RoleResponse(0L, "name", false);

        // Configure RoleRepository.findById(...).
        final Optional<Role> role = Optional.of(Role.builder()
                .roleId(0L)
                .name("name")
                .status(false)
                .build());
        when(mockRoleRepository.findById(0L)).thenReturn(role);

        // Configure RoleRepository.findByNameEquals(...).
        final Optional<Role> role1 = Optional.of(Role.builder()
                .roleId(0L)
                .name("name")
                .status(false)
                .build());
        when(mockRoleRepository.findByNameEquals("name")).thenReturn(role1);

        // Configure RoleRepository.save(...).
        final Role role2 = Role.builder()
                .roleId(0L)
                .name("name")
                .status(false)
                .build();
        when(mockRoleRepository.save(Role.builder()
                .roleId(0L)
                .name("name")
                .status(false)
                .build())).thenReturn(role2);

        // Run the test
        final RoleResponse result = roleServiceImplUnderTest.updateRole(0L, roleRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateRole_RoleRepositoryFindByIdReturnsAbsent() {
        // Setup
        final RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("name");
        roleRequest.setStatus(false);

        when(mockRoleRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> roleServiceImplUnderTest.updateRole(0L, roleRequest))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testUpdateRole_RoleRepositoryFindByNameEqualsReturnsAbsent() {
        // Setup
        final RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("name");
        roleRequest.setStatus(false);

        final RoleResponse expectedResult = new RoleResponse(0L, "name", false);

        // Configure RoleRepository.findById(...).
        final Optional<Role> role = Optional.of(Role.builder()
                .roleId(0L)
                .name("name")
                .status(false)
                .build());
        when(mockRoleRepository.findById(0L)).thenReturn(role);

        when(mockRoleRepository.findByNameEquals("name")).thenReturn(Optional.empty());

        // Configure RoleRepository.save(...).
        final Role role1 = Role.builder()
                .roleId(0L)
                .name("name")
                .status(false)
                .build();
        when(mockRoleRepository.save(Role.builder()
                .roleId(0L)
                .name("name")
                .status(false)
                .build())).thenReturn(role1);

        // Run the test
        final RoleResponse result = roleServiceImplUnderTest.updateRole(0L, roleRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeleteRole() {
        // Setup
        // Configure RoleRepository.findById(...).
        final Optional<Role> role = Optional.of(Role.builder()
                .roleId(0L)
                .name("name")
                .status(false)
                .build());
        when(mockRoleRepository.findById(0L)).thenReturn(role);

        // Run the test
        roleServiceImplUnderTest.deleteRole(0L);

        // Verify the results
        verify(mockRoleRepository).delete(Role.builder()
                .roleId(0L)
                .name("name")
                .status(false)
                .build());
    }

    @Test
    void testDeleteRole_RoleRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockRoleRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> roleServiceImplUnderTest.deleteRole(0L)).isInstanceOf(EntityNotFoundException.class);
    }
}
