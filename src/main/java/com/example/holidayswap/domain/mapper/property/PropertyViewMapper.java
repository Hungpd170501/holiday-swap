package com.example.holidayswap.domain.mapper.property;

import com.example.holidayswap.domain.dto.request.property.vacation.PropertyViewRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyViewResponse;
import com.example.holidayswap.domain.entity.property.PropertyView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyViewMapper {
    PropertyViewMapper INSTANCE = Mappers.getMapper(PropertyViewMapper.class);

    PropertyViewResponse toDtoResponse(PropertyView entity);

    PropertyView toEntity(PropertyViewRequest dtoRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PropertyViewRequest dto, @MappingTarget PropertyView entity);
}
