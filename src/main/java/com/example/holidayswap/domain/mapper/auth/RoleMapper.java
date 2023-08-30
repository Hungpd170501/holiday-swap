package com.example.holidayswap.domain.mapper.auth;

import com.example.holidayswap.domain.dto.request.auth.RoleRequest;
import com.example.holidayswap.domain.dto.response.auth.RoleResponse;
import com.example.holidayswap.domain.entity.auth.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}