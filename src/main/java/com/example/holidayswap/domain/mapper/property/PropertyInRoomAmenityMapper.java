package com.example.holidayswap.domain.mapper.property;

import com.example.holidayswap.domain.dto.request.property.PropertyImageRequest;
import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.PropertyInRoomAmenityRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyImageResponse;
import com.example.holidayswap.domain.entity.property.PropertyImage;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.PropertyInRoomAmenity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface PropertyInRoomAmenityMapper {
    PropertyInRoomAmenityMapper INSTANCE = Mappers.getMapper(PropertyInRoomAmenityMapper.class);

    PropertyImageResponse toPropertyImageResponse(PropertyImage propertyImage);

    PropertyInRoomAmenity toPropertyInRoomAmenity(PropertyInRoomAmenityRequest propertyInRoomAmenityRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PropertyImageRequest dto, @MappingTarget PropertyImage entity);
}
