package com.example.holidayswap.domain.mapper.property;

import com.example.holidayswap.domain.dto.request.property.ContractImageRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.ContractImageResponse;
import com.example.holidayswap.domain.entity.property.ContractImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContractImageMapper {
    ContractImageMapper INSTANCE = Mappers.getMapper(ContractImageMapper.class);

    ContractImageResponse toDtoResponse(ContractImage contractImage);

    @Mapping(target = "propertyContractId", ignore = true)
    ContractImage toEntity(ContractImageRequest contractImageRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ContractImageRequest dto, @MappingTarget ContractImage entity);
}
