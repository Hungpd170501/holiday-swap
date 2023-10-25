package com.example.holidayswap.domain.mapper.address;

import com.example.holidayswap.domain.dto.request.address.CountryRequest;
import com.example.holidayswap.domain.entity.address.Country;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    Country toCountry(CountryRequest countryRequest);
}
