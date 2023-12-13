package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyTypeRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyTypeResponse;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyTypeMapper;
import com.example.holidayswap.repository.property.PropertyTypeRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.holidayswap.constants.ErrorMessage.DUPLICATE_PROPERTY_TYPE;
import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_TYPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyTypeServiceImpl implements PropertyTypeService {
    private final PropertyTypeRespository propertyTypeRepository;
    private final PropertyTypeMapper propertyTypeMapper;

    @Override
    public Page<PropertyTypeResponse> gets(String name, Pageable pageable) {
        var entities = propertyTypeRepository.findAllByPropertyTypeNameContainingIgnoreCaseAndDeletedIsFalse(name, pageable);
        var dtoResponse = entities.map(PropertyTypeMapper.INSTANCE::toDtoResponse);
        return dtoResponse;
    }

    @Override
    public List<PropertyTypeResponse> gets() {
        var entity = propertyTypeRepository.findAllAndIsDeletedIsFasle();
        var dtoResponse = entity.stream().map(propertyTypeMapper::toDtoResponse).collect(Collectors.toList());
        return dtoResponse;
    }

    @Override
    public List<PropertyTypeResponse> getPropertyTypeInResort(Long resortId) {
        var list = propertyTypeRepository.findPropertyTypeIsInResort(resortId);
        return list.stream().map(propertyTypeMapper::toDtoResponse).collect(Collectors.toList());
    }

    @Override
    public PropertyTypeResponse get(Long id) {
        var entity = propertyTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        var dtoResponse = PropertyTypeMapper.INSTANCE.toDtoResponse(entity);
        return dtoResponse;
    }

    @Override
    public PropertyTypeResponse create(PropertyTypeRequest dtoRequest) {
        if (propertyTypeRepository.
                findByPropertyTypeNameEqualsIgnoreCaseAndDeletedIsFalse(dtoRequest.getPropertyTypeName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_PROPERTY_TYPE);

        var entity = PropertyTypeMapper.INSTANCE.toEntity(dtoRequest);
        var created = propertyTypeRepository.save(entity);
        var dtoResponse = PropertyTypeMapper.INSTANCE.toDtoResponse(created);
        return dtoResponse;
    }

    @Override
    public PropertyTypeResponse update(Long id, PropertyTypeRequest dtoRequest) {
        var entityFound = propertyTypeRepository.
                findByPropertyTypeNameEqualsIgnoreCaseAndDeletedIsFalse(dtoRequest.getPropertyTypeName());

        if (entityFound.isPresent() && !Objects.equals(entityFound.get().getId(), id))
            throw new DuplicateRecordException(DUPLICATE_PROPERTY_TYPE);

        var entity = propertyTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        PropertyTypeMapper.INSTANCE.updateEntityFromDTO(dtoRequest, entity);
        var updated = propertyTypeRepository.save(entity);
        var dtoResponse = PropertyTypeMapper.INSTANCE.toDtoResponse(updated);
        return dtoResponse;
    }

    @Override
    public void delete(Long id) {
        var entity = propertyTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        entity.setDeleted(true);
        propertyTypeRepository.save(entity);
    }
}
