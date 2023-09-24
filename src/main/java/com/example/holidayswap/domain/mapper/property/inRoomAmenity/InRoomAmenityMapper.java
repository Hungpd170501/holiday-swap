package com.example.holidayswap.domain.mapper.property.inRoomAmenity;

import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.InRoomAmenityRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityResponse;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InRoomAmenityMapper {
    InRoomAmenityMapper INSTANCE = Mappers.getMapper(InRoomAmenityMapper.class);

    InRoomAmenity toEntity(InRoomAmenityRequest inRoomAmenityTypeRequest);

    InRoomAmenityResponse toDtoResponse(InRoomAmenity inRoomAmenityType);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(InRoomAmenityRequest dto, @MappingTarget InRoomAmenity entity);
}
