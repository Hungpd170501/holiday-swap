package com.example.holidayswap.domain.mapper.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.mapper.auth.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface PropertyMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    PropertyResponse toPropertyResponse(Property property);

    Property toProperty(PropertyRegisterRequest propertyRegisterRequest);

    Property toProperty(PropertyUpdateRequest propertyUpdateRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PropertyUpdateRequest dto, @MappingTarget Property entity);
}
