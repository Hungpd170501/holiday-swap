package com.example.holidayswap.domain.mapper.property;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO;
import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ApartmentForRentMapper {
    ApartmentForRentMapper INSTANCE = Mappers.getMapper(ApartmentForRentMapper.class);

    ApartmentForRentResponse toDtoResponse(ApartmentForRentDTO entity);

    ApartmentForRentDTO toEntity(ApartmentForRentResponse dtoRequest);
}
