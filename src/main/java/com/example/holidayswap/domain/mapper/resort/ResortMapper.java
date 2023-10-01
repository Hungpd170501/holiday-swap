package com.example.holidayswap.domain.mapper.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.entity.resort.Resort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResortMapper {
    ResortMapper INSTANCE = Mappers.getMapper(ResortMapper.class);

    @Mapping(target = "amenities", source = "propertyTypes", ignore = true)
    @Mapping(target = "propertyTypes", source = "propertyTypes", ignore = true)
    Resort toResort(ResortRequest resortRequest);

    ResortResponse toResortResponse(Resort resort);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amenities", source = "propertyTypes", ignore = true)
    @Mapping(target = "propertyTypes", source = "propertyTypes", ignore = true)
    void updateEntityFromDTO(ResortRequest dto, @MappingTarget Resort entity);
}
