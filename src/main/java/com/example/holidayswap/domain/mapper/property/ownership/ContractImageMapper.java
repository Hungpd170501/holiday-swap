package com.example.holidayswap.domain.mapper.property.ownership;

import com.example.holidayswap.domain.dto.request.property.ownership.ContractImageRequest;
import com.example.holidayswap.domain.dto.response.property.ownership.ContractImageResponse;
import com.example.holidayswap.domain.entity.property.ownership.ContractImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContractImageMapper {
    ContractImageMapper INSTANCE = Mappers.getMapper(ContractImageMapper.class);

    ContractImageResponse toDtoResponse(ContractImage entity);

    ContractImage toEntity(ContractImageRequest dtoRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ContractImageRequest dto, @MappingTarget ContractImage entity);
}
