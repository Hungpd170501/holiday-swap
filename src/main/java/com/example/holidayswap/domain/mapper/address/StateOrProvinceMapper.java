package com.example.holidayswap.domain.mapper.address;

import com.example.holidayswap.domain.dto.request.address.StateOrProvinceRequest;
import com.example.holidayswap.domain.entity.address.StateOrProvince;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StateOrProvinceMapper {
    StateOrProvinceMapper INSTANCE = Mappers.getMapper(StateOrProvinceMapper.class);

    StateOrProvince toStateOrProvince(StateOrProvinceRequest stateOrProvinceRequest);
}
