package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.request.auth.RoleRequest;
import com.example.holidayswap.domain.dto.response.auth.RoleResponse;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.auth.RoleMapper;
import com.example.holidayswap.repository.auth.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.ROLE_ALREADY_EXISTS;
import static com.example.holidayswap.constants.ErrorMessage.ROLE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<RoleResponse> getRoles() {
        return roleRepository.findAll().stream().map(RoleMapper.INSTANCE::toRoleResponse).toList();
    }

    @Override
    @Cacheable(value = "role")
    public RoleResponse getRoleById(Long roleId) {
        return roleRepository.findById(roleId).map(RoleMapper.INSTANCE::toRoleResponse).orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));
    }

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        roleRepository.findByNameEquals(roleRequest.getName()).ifPresent(role -> {
            throw new DataIntegrityViolationException(ROLE_ALREADY_EXISTS);
        });
        return RoleMapper.INSTANCE.toRoleResponse(roleRepository.save(RoleMapper.INSTANCE.toRole(roleRequest)));
    }

    @Override
    @CachePut(value = "role", key = "#roleId")
    public RoleResponse updateRole(Long roleId, RoleRequest roleRequest) {
        return roleRepository.findById(roleId).map(role -> {
            role.setName(roleRequest.getName());
            roleRepository.findByNameEquals(roleRequest.getName()).ifPresent(r -> {
                throw new DataIntegrityViolationException(ROLE_ALREADY_EXISTS);
            });
            return RoleMapper.INSTANCE.toRoleResponse(roleRepository.save(role));
        }).orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));
    }

    @Override
    @CacheEvict(value = "role", key = "#roleId")
    public void deleteRole(Long roleId) {
        roleRepository.findById(roleId)
                .ifPresentOrElse(
                        roleRepository::delete,
                        () -> {
                            throw new EntityNotFoundException(ROLE_NOT_FOUND);
                        });
    }
}
