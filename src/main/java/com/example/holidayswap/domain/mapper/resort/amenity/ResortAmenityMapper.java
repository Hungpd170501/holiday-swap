package com.example.holidayswap.domain.mapper.resort.amenity;

import com.example.holidayswap.domain.dto.request.resort.amenity.ResortAmenityRequest;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityResponse;
import com.example.holidayswap.domain.entity.resort.amentity.ResortAmenity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResortAmenityMapper {
    ResortAmenityMapper INSTANCE = Mappers.getMapper(ResortAmenityMapper.class);

    ResortAmenity toEntity(ResortAmenityRequest dtoRequest);

    ResortAmenityResponse toDtoResponse(ResortAmenity entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ResortAmenityRequest dto, @MappingTarget ResortAmenity entity);
}
