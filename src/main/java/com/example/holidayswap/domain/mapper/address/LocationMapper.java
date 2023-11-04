package com.example.holidayswap.domain.mapper.address;

import com.example.holidayswap.domain.dto.request.address.LocationRequest;
import com.example.holidayswap.domain.dto.response.address.LocationResponse;
import com.example.holidayswap.domain.entity.resort.Resort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);
    @Mapping(target = "district.stateProvince", ignore = true)
    Resort toResort(LocationRequest locationRequest);

    @Mapping(target = "district.stateProvince.country.stateOrProvinces", ignore = true)
    @Mapping(target = "resortId", source = "id")
    LocationResponse toLocationResponse(Resort resort);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "resortName", ignore = true)
    @Mapping(target = "resortDescription", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "resortImages", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    @Mapping(target = "propertyTypes", ignore = true)
    @Mapping(target = "properties", ignore = true)
    void updateLocation(@MappingTarget Resort entity, Resort updateEntity);
}
