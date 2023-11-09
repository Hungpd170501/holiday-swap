package com.example.holidayswap.domain.mapper.property;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.holidayswap.domain.dto.response.property.ResortApartmentForRentDTO;
import com.example.holidayswap.domain.dto.response.property.ResortApartmentForRentResponse;

@Mapper(componentModel = "spring")
public interface ResortApartmentForRentMapper {
    ResortApartmentForRentMapper INSTANCE = Mappers.getMapper(ResortApartmentForRentMapper.class);

    ResortApartmentForRentResponse toDtoResponse(ResortApartmentForRentDTO entity);

    ResortApartmentForRentDTO toEntity(ResortApartmentForRentResponse dtoRequest);
}
