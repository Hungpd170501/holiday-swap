package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyViewRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyViewResponse;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyViewMapper;
import com.example.holidayswap.repository.property.PropertyViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.example.holidayswap.constants.ErrorMessage.DUPLICATE_PROPERTY_TYPE;
import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_TYPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyViewServiceImpl implements PropertyViewService {
    private final PropertyViewRepository propertyViewRepository;

    @Override
    public Page<PropertyViewResponse> gets(String name, Pageable pageable) {
        var entities = propertyViewRepository.
                findAllByPropertyViewNameContainingIgnoreCaseAndIsDeletedIsFalse(name, pageable);
        var dtoReponses = entities.map(PropertyViewMapper.INSTANCE::toDtoResponse);
        return dtoReponses;
    }

    @Override
    public PropertyViewResponse get(Long id) {
        var entity = propertyViewRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        var dtoRespone = PropertyViewMapper.INSTANCE.toDtoResponse(entity);
        return dtoRespone;
    }

    @Override
    public PropertyViewResponse create(PropertyViewRequest dtoRequest) {
        if (propertyViewRepository.
                findByPropertyViewNameEqualsIgnoreCaseAndIsDeletedIsFalse(dtoRequest.getPropertyViewName()).
                isPresent())
            throw new DuplicateRecordException(DUPLICATE_PROPERTY_TYPE);
        var entity = PropertyViewMapper.INSTANCE.toEntity(dtoRequest);
        var created = propertyViewRepository.save(entity);
        var dtoResponse = PropertyViewMapper.INSTANCE.toDtoResponse(created);
        return dtoResponse;
    }

    @Override
    public PropertyViewResponse update(Long id, PropertyViewRequest dtoRequest) {
        var entityFound = propertyViewRepository.
                findByPropertyViewNameEqualsIgnoreCaseAndIsDeletedIsFalse(dtoRequest.getPropertyViewName());

        if (entityFound.isPresent() && !Objects.equals(entityFound.get().getId(), id))
            throw new DuplicateRecordException(DUPLICATE_PROPERTY_TYPE);

        var entity = propertyViewRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        PropertyViewMapper.INSTANCE.updateEntityFromDTO(dtoRequest, entity);
        var updated = propertyViewRepository.save(entity);
        var dtoResponse = PropertyViewMapper.INSTANCE.toDtoResponse(updated);
        return dtoResponse;
    }

    @Override
    public void delete(Long id) {
        var entity = propertyViewRepository.
                findByIdAndIsDeletedIsFalse(id).orElseThrow(
                        () -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        entity.setDeleted(true);
        propertyViewRepository.save(entity);
    }
}
