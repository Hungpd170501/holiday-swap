package com.example.holidayswap.domain.mapper.auth;

import com.example.holidayswap.domain.dto.request.auth.RegisterRequest;
import com.example.holidayswap.domain.dto.request.auth.UserProfileUpdateRequest;
import com.example.holidayswap.domain.dto.request.auth.UserRequest;
import com.example.holidayswap.domain.dto.request.auth.UserUpdateRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserProfileResponse toUserProfileResponse(User user);

    @Mapping(source = "password", target = "passwordHash", ignore = true)
    @Mapping(source = "role", target = "role", ignore = true)
    User toUserEntity(RegisterRequest registerRequest);

    @Mapping(source = "password", target = "passwordHash", ignore = true)
    @Mapping(source = "avatar", target = "avatar", ignore = true)
    @Mapping(target = "role.roleId", source = "roleId", ignore = true)
    User toUserEntity(UserRequest userRequest);

    @Mapping(source = "avatar", target = "avatar", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "moneyTranfers", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "userBlockedList", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "bookingList", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "conversationParticipants", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    User toUserEntity(@MappingTarget User entity, UserUpdateRequest updateEntity);

    @Mapping(source = "avatar", target = "avatar", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "moneyTranfers", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "userBlockedList", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "bookingList", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "conversationParticipants", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "phoneVerified", ignore = true)
    @Mapping(target = "status", ignore = true)
    User toUserEntity(@MappingTarget User entity, UserProfileUpdateRequest updateEntity);
}
