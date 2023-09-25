package com.example.holidayswap.domain.mapper.resort.amenity;

import com.example.holidayswap.domain.dto.request.resort.amenity.ResortAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
import com.example.holidayswap.domain.entity.resort.amentity.ResortAmenityType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResortAmenityTypeMapper {
    ResortAmenityTypeMapper INSTANCE = Mappers.getMapper(ResortAmenityTypeMapper.class);

    ResortAmenityType toEntity(ResortAmenityTypeRequest dtoRequest);

    ResortAmenityTypeResponse toDtoResponse(ResortAmenityType entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ResortAmenityTypeRequest dto, @MappingTarget ResortAmenityType entity);
}
