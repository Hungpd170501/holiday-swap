package com.example.holidayswap.domain.mapper.property.inRoomAmenity;

import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.InRoomAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityTypeResponse;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenityType;
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
