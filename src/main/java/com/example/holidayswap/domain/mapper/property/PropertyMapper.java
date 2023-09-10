package com.example.holidayswap.domain.mapper.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.Property;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyMapper {
    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

    Property toProperty(PropertyRequest propertyRequest);

    PropertyResponse toPropertyResponse(Property property);
}
