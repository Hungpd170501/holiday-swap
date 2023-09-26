package com.example.holidayswap.domain.mapper.property.amenity;

import com.example.holidayswap.domain.dto.request.property.amenity.InRoomAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityTypeResponse;
import com.example.holidayswap.domain.entity.property.amenity.InRoomAmenityType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InRoomAmenityTypeMapper {
    InRoomAmenityTypeMapper INSTANCE = Mappers.getMapper(InRoomAmenityTypeMapper.class);

    InRoomAmenityType toEntity(InRoomAmenityTypeRequest inRoomAmenityTypeRequest);

    InRoomAmenityTypeResponse toDtoResponse(InRoomAmenityType inRoomAmenityType);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(InRoomAmenityTypeRequest dto, @MappingTarget InRoomAmenityType entity);
}
