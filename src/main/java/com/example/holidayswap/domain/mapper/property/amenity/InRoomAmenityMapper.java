package com.example.holidayswap.domain.mapper.property.amenity;

import com.example.holidayswap.domain.dto.request.property.amenity.InRoomAmenityRequest;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityResponse;
import com.example.holidayswap.domain.entity.property.amenity.InRoomAmenity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InRoomAmenityMapper {
    InRoomAmenityMapper INSTANCE = Mappers.getMapper(InRoomAmenityMapper.class);

    InRoomAmenity toEntity(InRoomAmenityRequest dtoRequest);

    InRoomAmenityResponse toDtoResponse(InRoomAmenity entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(InRoomAmenityRequest dto, @MappingTarget InRoomAmenity entity);
}
