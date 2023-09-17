package com.example.holidayswap.domain.mapper.property.inRoomAmenity;

import com.example.holidayswap.domain.dto.request.property.PropertyContractRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyContractResponse;
import com.example.holidayswap.domain.entity.property.PropertyContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyContractMapper {
    PropertyContractMapper INSTANCE = Mappers.getMapper(PropertyContractMapper.class);

    PropertyContractResponse toPropertyContractResponse(PropertyContract propertyContract);

    @Mapping(target = "propertyId", ignore = true)
    PropertyContract toPropertyContract(PropertyContractRequest propertyContractRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PropertyContractRequest dto, @MappingTarget PropertyContract entity);
}
