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

    @Override
    public Page<ResortAmenityTypeResponse> gets(String name, Pageable pageable) {
        return resortAmenityTypeRepository.findAllByResortAmenityTypeNameContainingIgnoreCaseAndIsDeletedFalse(name, pageable).map(ResortAmenityTypeMapper.INSTANCE::toDtoResponse);
    }

    @Override
    public List<ResortAmenityTypeResponse> gets(Long resortId) {
        return resortAmenityTypeRepository.findAllByResortId(resortId).stream().map(ResortAmenityTypeMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public ResortAmenityTypeResponse get(Long id) {
        return ResortAmenityTypeMapper.INSTANCE.toDtoResponse(resortAmenityTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.RESORT_AMENITY_TYPE_NOT_FOUND)));
    }

    @Override
    public ResortAmenityTypeResponse create(ResortAmenityTypeRequest dtoRequest) {
        var entity = resortAmenityTypeRepository.findByResortAmenityTypeNameContainingIgnoreCaseAndIsDeletedFalse(dtoRequest.getResortAmenityTypeName());
        if (entity.isPresent()) throw new DuplicateRecordException(DUPLICATE_RESORT_AMENITY_TYPE);
        return ResortAmenityTypeMapper.INSTANCE.toDtoResponse(resortAmenityTypeRepository.save(ResortAmenityTypeMapper.INSTANCE.toEntity(dtoRequest)));
    }

    @Override
    public ResortAmenityTypeResponse update(Long id, ResortAmenityTypeRequest dtoRequest) {
        var entity = resortAmenityTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(RESORT_AMENITY_TYPE_NOT_FOUND));
        if (resortAmenityTypeRepository.findByResortAmenityTypeNameContainingIgnoreCaseAndIsDeletedFalse(dtoRequest.getResortAmenityTypeName()).isPresent())
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
