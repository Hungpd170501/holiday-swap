package com.example.holidayswap.controller.auth;

import com.example.holidayswap.domain.dto.request.auth.RoleRequest;
import com.example.holidayswap.domain.dto.response.auth.RoleResponse;
import com.example.holidayswap.service.auth.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }


    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponse> getUserById(@PathVariable("roleId") Long roleId) {
        return ResponseEntity.ok(roleService.getRoleById(roleId));
    }

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        return ResponseEntity.ok(roleService.createRole(roleRequest));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable("roleId") Long roleId, @RequestBody RoleRequest roleRequest) {
        return ResponseEntity.ok(roleService.updateRole(roleId, roleRequest));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable("roleId") Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}