package com.example.holidayswap.domain.mapper.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface PropertyMapper {
    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);
    PropertyResponse toPropertyResponse(Property property);

    @Mapping(target = "propertyContracts", ignore = true)
    Property toProperty(PropertyRegisterRequest propertyRegisterRequest);

    Property toProperty(PropertyUpdateRequest propertyUpdateRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PropertyUpdateRequest dto, @MappingTarget Property entity);
}
