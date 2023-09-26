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

import static com.example.holidayswap.constants.ErrorMessage.DUPLICATE_RESORT_AMENITY_TYPE;
import static com.example.holidayswap.constants.ErrorMessage.RESORT_AMENITY_TYPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ResortAmenityTypeServiceImpl implements ResortAmenityTypeService {
    private final ResortAmenityTypeRepository resortAmenityTypeRepository;
    private final ResortAmenityService resortAmenityService;

    @Override
    public Page<ResortAmenityTypeResponse> gets(String name, Pageable pageable) {
        var dto =
                resortAmenityTypeRepository.findAllByResortAmenityTypeNameContainingIgnoreCaseAndIsDeletedFalse(name, pageable).
                        map(ResortAmenityTypeMapper.INSTANCE::toDtoResponse);
        dto.forEach(e -> {
            e.setResortAmenities(resortAmenityService.gets(e.getId()));
        });
        return dto;
    }

    @Override
    public List<ResortAmenityTypeResponse> gets(Long resortId) {
        var dto = resortAmenityTypeRepository.findAllByResortId(resortId).stream().map(
                ResortAmenityTypeMapper.INSTANCE::toDtoResponse).toList();
        dto.forEach(e -> {
            e.setResortAmenities(resortAmenityService.gets(e.getId(), resortId));
        });
        return dto;
    }

    @Override
    public ResortAmenityTypeResponse get(Long id) {
        var dto = ResortAmenityTypeMapper.INSTANCE.toDtoResponse(resortAmenityTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.RESORT_AMENITY_TYPE_NOT_FOUND)));
        dto.setResortAmenities(resortAmenityService.gets(dto.getId()));
        return dto;
    }

    @Override
    public ResortAmenityTypeResponse create(ResortAmenityTypeRequest dtoRequest) {
        var entity = resortAmenityTypeRepository.findByResortAmenityTypeNameEqualsIgnoreCaseAndIsDeletedFalse(dtoRequest.getResortAmenityTypeName());
        if (entity.isPresent()) throw new DuplicateRecordException(DUPLICATE_RESORT_AMENITY_TYPE);
        return ResortAmenityTypeMapper.INSTANCE.toDtoResponse(resortAmenityTypeRepository.save(ResortAmenityTypeMapper.INSTANCE.toEntity(dtoRequest)));
    }

    @Override
    public ResortAmenityTypeResponse update(Long id, ResortAmenityTypeRequest dtoRequest) {
        var entity = resortAmenityTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(RESORT_AMENITY_TYPE_NOT_FOUND));
        if (resortAmenityTypeRepository.findByResortAmenityTypeNameEqualsIgnoreCaseAndIsDeletedFalse(dtoRequest.getResortAmenityTypeName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_RESORT_AMENITY_TYPE);
        ResortAmenityTypeMapper.INSTANCE.updateEntityFromDTO(dtoRequest, entity);
        return ResortAmenityTypeMapper.INSTANCE.toDtoResponse(resortAmenityTypeRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = resortAmenityTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(RESORT_AMENITY_TYPE_NOT_FOUND));
        entity.setIsDeleted(true);
        resortAmenityTypeRepository.save(entity);
    }
}
