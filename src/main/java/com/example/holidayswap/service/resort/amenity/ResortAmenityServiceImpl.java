package com.example.holidayswap.service.resort.amenity;

import com.example.holidayswap.domain.dto.request.resort.amenity.ResortAmenityRequest;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityResponse;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.resort.amenity.ResortAmenityMapper;
import com.example.holidayswap.repository.resort.amenity.ResortAmenityRepository;
import com.example.holidayswap.repository.resort.amenity.ResortAmenityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ResortAmenityServiceImpl implements ResortAmenityService {
    private final ResortAmenityRepository resortAmenityRepository;
    private final ResortAmenityTypeRepository resortAmenityTypeRepository;

    @Override
    public Page<ResortAmenityResponse> gets(String name, Pageable pageable) {
        return resortAmenityRepository.findAllByResortAmenityName(name, pageable).map(ResortAmenityMapper.INSTANCE::toDtoResponse);
    }

    @Override
    public Page<ResortAmenityResponse> gets(Long amenityTypeId, Pageable pageable) {
        return resortAmenityRepository.findAllByResortAmenityTypeId(amenityTypeId, pageable).map(ResortAmenityMapper.INSTANCE::toDtoResponse);
    }

    @Override
    public List<ResortAmenityResponse> gets(Long amenityTypeId) {
        return resortAmenityRepository.findAllByResortAmenityTypeId(amenityTypeId).stream().map(ResortAmenityMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public List<ResortAmenityResponse> gets(Long amenityTypeId, Long resortId) {
        var amentities = resortAmenityRepository.findAllByResortAmenityTypeIdAndResortId(amenityTypeId, resortId);
        return amentities.stream().map(ResortAmenityMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public ResortAmenityResponse get(Long id) {
        return ResortAmenityMapper.INSTANCE.toDtoResponse(resortAmenityRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(RESORT_AMENITY_NOT_FOUND)));
    }

    @Override
    public ResortAmenityResponse create(ResortAmenityRequest dtoRequest) {
        if (resortAmenityRepository.findByResortAmenityNameEqualsIgnoreCaseAndIsDeletedIsFalse(dtoRequest.getResortAmenityName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_RESORT_AMENITY);
        if (resortAmenityTypeRepository.findByIdAndIsDeletedFalse(dtoRequest.getResortAmenityTypeId()).isEmpty())
            throw new DataIntegrityViolationException(RESORT_AMENITY_TYPE_DELETED);
        return ResortAmenityMapper.INSTANCE.toDtoResponse(resortAmenityRepository.save(ResortAmenityMapper.INSTANCE.toEntity(dtoRequest)));
    }

    @Override
    public ResortAmenityResponse update(Long id, ResortAmenityRequest dtoRequest) {
        var entity = resortAmenityRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(RESORT_AMENITY_NOT_FOUND));
        var entityFound = resortAmenityRepository.findByResortAmenityNameEqualsIgnoreCaseAndIsDeletedIsFalse(dtoRequest.getResortAmenityName());
        if (entityFound.isPresent() && !Objects.equals(entityFound.get().getId(), id))
            throw new DuplicateRecordException(DUPLICATE_RESORT_AMENITY);
        if (resortAmenityTypeRepository.findByIdAndIsDeletedFalse(dtoRequest.getResortAmenityTypeId()).isEmpty())
            throw new DataIntegrityViolationException(RESORT_AMENITY_TYPE_DELETED);
        ResortAmenityMapper.INSTANCE.updateEntityFromDTO(dtoRequest, entity);
        return ResortAmenityMapper.INSTANCE.toDtoResponse(resortAmenityRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = resortAmenityRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(RESORT_AMENITY_NOT_FOUND));
        entity.setIsDeleted(true);
        resortAmenityRepository.save(entity);
    }
}
