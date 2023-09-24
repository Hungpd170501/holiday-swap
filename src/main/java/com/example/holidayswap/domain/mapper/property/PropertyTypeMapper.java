package com.example.holidayswap.domain.mapper.property;

import com.example.holidayswap.domain.dto.request.property.PropertyTypeRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyTypeResponse;
import com.example.holidayswap.domain.entity.property.PropertyType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyTypeMapper {
    PropertyTypeMapper INSTANCE = Mappers.getMapper(PropertyTypeMapper.class);

    PropertyTypeResponse toDtoResponse(PropertyType propertyType);

    PropertyType toEntity(PropertyTypeRequest propertyTypeRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PropertyTypeRequest dto, @MappingTarget PropertyType entity);
}
