package com.example.holidayswap.service.auth;

import com.example.holidayswap.domain.dto.request.auth.RoleRequest;
import com.example.holidayswap.domain.dto.response.auth.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> getRoles();

    RoleResponse getRoleById(Long roleId);

    RoleResponse createRole(RoleRequest roleRequest);

    RoleResponse updateRole(Long roleId, RoleRequest roleRequest);

    void deleteRole(Long roleId);
}
