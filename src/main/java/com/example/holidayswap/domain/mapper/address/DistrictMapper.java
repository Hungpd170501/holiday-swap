package com.example.holidayswap.domain.mapper.address;

import com.example.holidayswap.domain.dto.request.address.DistrictRequest;
import com.example.holidayswap.domain.entity.address.District;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DistrictMapper {
    DistrictMapper INSTANCE = Mappers.getMapper(DistrictMapper.class);

    District toDistrict(DistrictRequest districtRequest);
}
