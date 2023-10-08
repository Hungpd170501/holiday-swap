package com.example.holidayswap.domain.mapper.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.ContractImageRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.ContractImageResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.ContractImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContractImageMapper {
    ContractImageMapper INSTANCE = Mappers.getMapper(ContractImageMapper.class);

    ContractImageResponse toDtoResponse(ContractImage entity);

    ContractImage toEntity(ContractImageRequest dtoRequest);

    ContractImage toEntityFromEmbeddedId(CoOwnerId dtoRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ContractImageRequest dto, @MappingTarget ContractImage entity);
}
