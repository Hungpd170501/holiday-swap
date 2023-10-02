package com.example.holidayswap.domain.mapper.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface PropertyMapper {
    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);
    PropertyResponse toDtoResponse(Property entity);

    @Mapping(target = "inRoomAmenities", ignore = true)
//    @Mapping(target = "ownershipRequest" , ignore = true)
    Property toEntity(PropertyRegisterRequest dtoRequest);

    Property toEntity(PropertyUpdateRequest dtoRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PropertyUpdateRequest dto, @MappingTarget Property entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "inRoomAmenities", target = "inRoomAmenities", ignore = true)
    void updateEntityFromDTO(PropertyRegisterRequest dto, @MappingTarget Property entity);
}
