package com.example.holidayswap.domain.mapper.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.Property;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyMapper {
    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

    PropertyResponse toPropertyResponse(Property property);

    Property toProperty(PropertyRegisterRequest propertyRegisterRequest);

    Property toProperty(PropertyUpdateRequest propertyUpdateRequest);
}
