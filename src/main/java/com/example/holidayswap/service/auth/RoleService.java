package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.request.auth.RoleRequest;
import com.example.holidayswap.domain.dto.response.auth.RoleResponse;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.auth.RoleMapper;
import com.example.holidayswap.repository.auth.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public static final String ROLE_NOT_FOUND = "role not found.";

    public List<RoleResponse> getRoles() {
        return roleRepository.findAll().stream().map(RoleMapper.INSTANCE::toRoleResponse).collect(Collectors.toList());
    }

    public RoleResponse getRoleById(Long roleId) {
        return roleRepository.findById(roleId).map(RoleMapper.INSTANCE::toRoleResponse).orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));
    }

    public RoleResponse createRole(RoleRequest roleRequest) {
        roleRepository.findByNameEquals(roleRequest.getName()).ifPresent(role -> {
            throw new DataIntegrityViolationException("Role already exists.");
        });
        return RoleMapper.INSTANCE.toRoleResponse(roleRepository.save(RoleMapper.INSTANCE.toRole(roleRequest)));
    }

    public RoleResponse updateRole(Long roleId, RoleRequest roleRequest) {
        return roleRepository.findById(roleId).map(role -> {
            role.setName(roleRequest.getName());
            roleRepository.findByNameEquals(roleRequest.getName()).ifPresent(r -> {
                throw new DataIntegrityViolationException("Role already exists.");
            });
            return RoleMapper.INSTANCE.toRoleResponse(roleRepository.save(role));
        }).orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));
    }

    public void deleteRole(Long roleId) {
        roleRepository.findById(roleId).ifPresentOrElse(roleRepository::delete, () -> {
            throw new EntityNotFoundException(ROLE_NOT_FOUND);
        });
    }
}
