package com.example.holidayswap.controller.auth;

import com.example.holidayswap.domain.dto.request.auth.RoleRequest;
import com.example.holidayswap.domain.dto.response.auth.RoleResponse;
import com.example.holidayswap.service.auth.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @Cacheable(value = "roles")
    public ResponseEntity<List<RoleResponse>> getRoles() {
        var roles = roleService.getRoles();
        return ResponseEntity.ok(roles);
    }


    @GetMapping("/{roleId}")
    @Cacheable(value = "role")
    public ResponseEntity<RoleResponse> getUserById(@PathVariable("roleId") Long roleId) {
        var role = roleService.getRoleById(roleId);
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        var role = roleService.createRole(roleRequest);
        return ResponseEntity.ok(role);
    }

    @PutMapping("/{roleId}")
    @CacheEvict(value = "role", key = "#roleId")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable("roleId") Long roleId, @RequestBody RoleRequest roleRequest) {
        var role = roleService.updateRole(roleId, roleRequest);
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/{roleId}")
    @CacheEvict(value = "role", key = "#roleId")
    public ResponseEntity<Void> deleteRole(@PathVariable("roleId") Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}