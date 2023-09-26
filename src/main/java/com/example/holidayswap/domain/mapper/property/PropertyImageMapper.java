package com.example.holidayswap.domain.mapper.property;

import com.example.holidayswap.domain.dto.request.property.PropertyImageRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyImageResponse;
import com.example.holidayswap.domain.entity.property.PropertyImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyImageMapper {
    PropertyImageMapper INSTANCE = Mappers.getMapper(PropertyImageMapper.class);

    PropertyImageResponse toDtoResponse(PropertyImage dtoRequest);

    PropertyImage toEntity(PropertyImageRequest entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PropertyImageRequest dto, @MappingTarget PropertyImage entity);
}
