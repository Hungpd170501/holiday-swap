package com.example.holidayswap.service.resort.amenity;

import com.example.holidayswap.constants.ErrorMessage;
import com.example.holidayswap.domain.dto.request.resort.amenity.ResortAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.resort.amenity.ResortAmenityTypeMapper;
import com.example.holidayswap.repository.resort.amenity.ResortAmenityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.example.holidayswap.constants.ErrorMessage.DUPLICATE_RESORT_AMENITY_TYPE;
import static com.example.holidayswap.constants.ErrorMessage.RESORT_AMENITY_TYPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ResortAmenityTypeServiceImpl implements ResortAmenityTypeService {
    private final ResortAmenityTypeRepository resortAmenityTypeRepository;
    private final ResortAmenityService resortAmenityService;

    @Override
    public Page<ResortAmenityTypeResponse> gets(String name, Pageable pageable) {
        var entities = resortAmenityTypeRepository.findAllByResortAmenityTypeNameContainingIgnoreCaseAndIsDeletedFalse(name, pageable);

        var dtoReponse = entities.map(ResortAmenityTypeMapper.INSTANCE::toDtoResponse);
        dtoReponse.forEach(e -> {
            e.setResortAmenities(resortAmenityService.gets(e.getId()));
        });
        return dtoReponse;
    }

    @Override
    public List<ResortAmenityTypeResponse> gets(Long resortId) {
        var entities = resortAmenityTypeRepository.findAllByResortId(resortId);

        var dtoReponse = entities.stream().map(
                ResortAmenityTypeMapper.INSTANCE::toDtoResponse).toList();
        dtoReponse.forEach(e -> {
            e.setResortAmenities(resortAmenityService.gets(e.getId(), resortId));
        });
        return dtoReponse;
    }

    @Override
    public ResortAmenityTypeResponse get(Long id) {
        var entity = resortAmenityTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.RESORT_AMENITY_TYPE_NOT_FOUND));
        var dtoReponse = ResortAmenityTypeMapper.INSTANCE.toDtoResponse(entity);
        dtoReponse.setResortAmenities(resortAmenityService.gets(dtoReponse.getId()));
        return dtoReponse;
    }

    @Override
    public ResortAmenityTypeResponse create(ResortAmenityTypeRequest dtoRequest) {
        var entity = resortAmenityTypeRepository.
                findByResortAmenityTypeNameEqualsIgnoreCaseAndIsDeletedFalse(dtoRequest.getResortAmenityTypeName());

        if (entity.isPresent()) throw new DuplicateRecordException(DUPLICATE_RESORT_AMENITY_TYPE);

        var created = resortAmenityTypeRepository.save(ResortAmenityTypeMapper.INSTANCE.toEntity(dtoRequest));
        var dtoResponse = ResortAmenityTypeMapper.INSTANCE.toDtoResponse(created);
        return dtoResponse;
    }

    @Override
    public ResortAmenityTypeResponse update(Long id, ResortAmenityTypeRequest dtoRequest) {
        var entity = resortAmenityTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(RESORT_AMENITY_TYPE_NOT_FOUND));

        var entityFound = resortAmenityTypeRepository.
                findByResortAmenityTypeNameEqualsIgnoreCaseAndIsDeletedFalse(dtoRequest.getResortAmenityTypeName());
        if (entityFound.isPresent() && !Objects.equals(entityFound.get().getId(), id))
            throw new DuplicateRecordException(DUPLICATE_RESORT_AMENITY_TYPE);
        ResortAmenityTypeMapper.INSTANCE.updateEntityFromDTO(dtoRequest, entity);
        var updated = resortAmenityTypeRepository.save(entity);
        var dtoResponse = ResortAmenityTypeMapper.INSTANCE.toDtoResponse(updated);
        return dtoResponse;
    }

    @Override
    public void delete(Long id) {
        var entity = resortAmenityTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(RESORT_AMENITY_TYPE_NOT_FOUND));
        entity.setIsDeleted(true);
        resortAmenityTypeRepository.save(entity);
    }
}
