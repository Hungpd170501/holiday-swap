package com.example.holidayswap.domain.mapper.auth;

import com.example.holidayswap.domain.dto.request.auth.RegisterRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserProfileResponse toUserProfileResponse(User user);
    @Mapping(source = "password",target = "passwordHash", ignore = true)
    User toUserEntity(RegisterRequest registerRequest);
}
