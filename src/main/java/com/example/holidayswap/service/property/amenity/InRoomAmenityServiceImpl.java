package com.example.holidayswap.service.property.amenity;

import com.example.holidayswap.domain.dto.request.property.amenity.InRoomAmenityRequest;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityResponse;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.amenity.InRoomAmenityMapper;
import com.example.holidayswap.repository.property.amenity.InRoomAmenityRepository;
import com.example.holidayswap.repository.property.amenity.InRoomAmenityTypeRepository;
import com.example.holidayswap.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class InRoomAmenityServiceImpl implements InRoomAmenityService {
    private final InRoomAmenityRepository inRoomAmenityRepository;
    private final InRoomAmenityTypeRepository inRoomAmenityTypeRepository;
    private final FileService fileService;

    @Override
    public Page<InRoomAmenityResponse> gets(String name, Pageable pageable) {
        var entities = inRoomAmenityRepository.
                findAllByInRoomAmenitiesName(name, pageable);
        var dtoResponses = entities.map(InRoomAmenityMapper.INSTANCE::toDtoResponse);
        return dtoResponses;
    }

    @Override
    public Page<InRoomAmenityResponse> gets(Long inRoomAmenityTypeId, Pageable pageable) {
        var entities = inRoomAmenityRepository.
                findAllByInRoomAmenityTypeId(inRoomAmenityTypeId, pageable);
        var dtoResponses = entities.
                map(InRoomAmenityMapper.INSTANCE::toDtoResponse);
        return dtoResponses;
    }

    @Override
    public List<InRoomAmenityResponse> gets(Long propertyId) {
        var entities = inRoomAmenityRepository.findAllByInRoomAmenityTypeId(propertyId);
        var dtoResponses = entities.stream().map(InRoomAmenityMapper.INSTANCE::toDtoResponse).toList();
        return dtoResponses;
    }

    @Override
    public List<InRoomAmenityResponse> getByInRoomAmenityType(Long InRoomAmenityTypeId) {
        var entities = inRoomAmenityRepository.findAllByInRoomAmenityTypeId(InRoomAmenityTypeId);
        var dtoResponses = entities.stream().map(InRoomAmenityMapper.INSTANCE::toDtoResponse).toList();
        return dtoResponses;
    }

    @Override
    public List<InRoomAmenityResponse> gets() {
        var entities = inRoomAmenityRepository.findAll();
        var dtoResponses = entities.stream().map(InRoomAmenityMapper.INSTANCE::toDtoResponse).toList();
        return dtoResponses;
    }

    @Override
    public List<InRoomAmenityResponse> gets(Long propertyId, Long inRoomAmenityTypeId) {
        var amenities =
                inRoomAmenityRepository.findAllByPropertyIdAndAmenityTypeId(propertyId, inRoomAmenityTypeId);
        return amenities.stream().map(InRoomAmenityMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public InRoomAmenityResponse get(Long id) {
        return InRoomAmenityMapper.INSTANCE.toDtoResponse(inRoomAmenityRepository.
                findByInRoomAmenityTypeIdIdAndIsDeletedFalse(id).orElseThrow(
                        () -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND)));
    }

    @Override
    public InRoomAmenityResponse create(InRoomAmenityRequest dtoRequest, MultipartFile inRoomAmenityIcon) {
        if (inRoomAmenityRepository.findByInRoomAmenityNameEqualsIgnoreCaseAndIsDeletedFalse(dtoRequest.getInRoomAmenityName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_IN_ROOM_AMENITY);

        if (inRoomAmenityTypeRepository.findByInRoomAmenityTypeIdAndIsDeletedFalse(dtoRequest.getInRoomAmenityTypeId()).isEmpty())
            throw new DataIntegrityViolationException(IN_ROOM_AMENITY_TYPE_DELETED);
        String link = null;
        try {
            link = fileService.uploadFile(inRoomAmenityIcon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var entity = InRoomAmenityMapper.INSTANCE.toEntity(dtoRequest);
        entity.setInRoomAmenityLinkIcon(link);
        return InRoomAmenityMapper.INSTANCE.toDtoResponse(inRoomAmenityRepository.save(entity));
    }

    @Override
    public InRoomAmenityResponse update(Long id, InRoomAmenityRequest dtoRequest, MultipartFile inRoomAmenityIcon) {
        var entity = inRoomAmenityRepository.findByInRoomAmenityTypeIdIdAndIsDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND));
        var entityFound = inRoomAmenityRepository.findByInRoomAmenityNameEqualsIgnoreCaseAndIsDeletedFalse(dtoRequest.getInRoomAmenityName());
        if (entityFound.isPresent() && !Objects.equals(entityFound.get().getId(), id)) {
            throw new DuplicateRecordException(DUPLICATE_IN_ROOM_AMENITY);
        }
        if (inRoomAmenityTypeRepository.findByInRoomAmenityTypeIdAndIsDeletedFalse(dtoRequest.getInRoomAmenityTypeId()).isEmpty())
            throw new DataIntegrityViolationException(IN_ROOM_AMENITY_TYPE_DELETED);

        if (inRoomAmenityIcon != null) {
            String link = null;
            try {
                link = fileService.uploadFile(inRoomAmenityIcon);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            entity.setInRoomAmenityLinkIcon(link);
        }
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
