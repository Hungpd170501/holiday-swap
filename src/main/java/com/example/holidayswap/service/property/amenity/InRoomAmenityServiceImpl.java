package com.example.holidayswap.service.property.amenity;

import com.example.holidayswap.domain.dto.request.property.amenity.InRoomAmenityRequest;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityResponse;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.amenity.InRoomAmenityMapper;
import com.example.holidayswap.repository.property.amenity.InRoomAmenityRepository;
import com.example.holidayswap.repository.property.amenity.InRoomAmenityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class InRoomAmenityServiceImpl implements InRoomAmenityService {
    private final InRoomAmenityRepository inRoomAmenityRepository;
    private final InRoomAmenityTypeRepository inRoomAmenityTypeRepository;

    @Override
    public Page<InRoomAmenityResponse> gets(String name, Pageable pageable) {
        return inRoomAmenityRepository.findAllByInRoomAmenitiesName(name, pageable).map(InRoomAmenityMapper.INSTANCE::toDtoResponse);
    }

    @Override
    public Page<InRoomAmenityResponse> gets(Long inRoomAmenityTypeId, Pageable pageable) {
        return inRoomAmenityRepository.findAllByInRoomAmenityTypeId(inRoomAmenityTypeId, pageable).map(InRoomAmenityMapper.INSTANCE::toDtoResponse);
    }

    @Override
    public List<InRoomAmenityResponse> gets(Long propertyId) {
        return inRoomAmenityRepository.findAllByInRoomAmenityTypeId(propertyId).stream().map(InRoomAmenityMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public List<InRoomAmenityResponse> gets(Long propertyId, Long inRoomAmenityTypeId) {
        var amentities = inRoomAmenityRepository.findAllByInRoomAmenityTypeIdAndResortId(inRoomAmenityTypeId, propertyId);
        return amentities.stream().map(InRoomAmenityMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public InRoomAmenityResponse get(Long id) {
        return InRoomAmenityMapper.INSTANCE.toDtoResponse(inRoomAmenityRepository.findByInRoomAmenityTypeIdIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND)));
    }

    @Override
    public InRoomAmenityResponse create(InRoomAmenityRequest dtoRequest) {
        if (inRoomAmenityRepository.findByInRoomAmenityNameEqualsIgnoreCaseAndIsDeletedFalse(dtoRequest.getInRoomAmenityName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_INROOM_AMENITY);
        if (inRoomAmenityTypeRepository.findByInRoomAmenityTypeIdAndIsDeletedFalse(dtoRequest.getInRoomAmenityTypeId()).isEmpty())
            throw new DataIntegrityViolationException(INROOM_AMENITY_TYPE_DELETED);
        return InRoomAmenityMapper.INSTANCE.toDtoResponse(inRoomAmenityRepository.save(InRoomAmenityMapper.INSTANCE.toEntity(dtoRequest)));
    }

    @Override
    public InRoomAmenityResponse update(Long id, InRoomAmenityRequest dtoRequest) {
        var entity = inRoomAmenityRepository.findByInRoomAmenityTypeIdIdAndIsDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND));
        if (inRoomAmenityRepository.findByInRoomAmenityNameEqualsIgnoreCaseAndIsDeletedFalse(dtoRequest.getInRoomAmenityName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_INROOM_AMENITY);
        if (inRoomAmenityTypeRepository.findByInRoomAmenityTypeIdAndIsDeletedFalse(dtoRequest.getInRoomAmenityTypeId()).isEmpty())
            throw new DataIntegrityViolationException(INROOM_AMENITY_TYPE_DELETED);
        InRoomAmenityMapper.INSTANCE.updateEntityFromDTO(dtoRequest, entity);
        return InRoomAmenityMapper.INSTANCE.toDtoResponse(inRoomAmenityRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = inRoomAmenityRepository.findByInRoomAmenityTypeIdIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND));
        entity.setIsDeleted(true);
        inRoomAmenityRepository.save(entity);
    }
}
