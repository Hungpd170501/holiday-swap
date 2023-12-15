package com.example.holidayswap.service.resort.amenity;

import com.example.holidayswap.domain.dto.request.resort.amenity.ResortAmenityRequest;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityResponse;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.resort.amenity.ResortAmenityMapper;
import com.example.holidayswap.repository.resort.amenity.ResortAmenityRepository;
import com.example.holidayswap.repository.resort.amenity.ResortAmenityTypeRepository;
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
public class ResortAmenityServiceImpl implements ResortAmenityService {
    private final ResortAmenityRepository resortAmenityRepository;
    private final ResortAmenityTypeRepository resortAmenityTypeRepository;
    private final FileService fileService;

    @Override
    public Page<ResortAmenityResponse> gets(String name, Pageable pageable) {
        var entities = resortAmenityRepository.findAllByResortAmenityName(name, pageable);

        var dtoRespones = entities.map(ResortAmenityMapper.INSTANCE::toDtoResponse);
        return dtoRespones;
    }

    @Override
    public Page<ResortAmenityResponse> gets(Long amenityTypeId, Pageable pageable) {
        var entities = resortAmenityRepository.findAllByResortAmenityTypeId(amenityTypeId, pageable);

        var dtoRespones = entities.map(ResortAmenityMapper.INSTANCE::toDtoResponse);
        return dtoRespones;
    }

    @Override
    public List<ResortAmenityResponse> gets(Long amenityTypeId) {
        var entities = resortAmenityRepository.findAllByResortAmenityTypeId(amenityTypeId);

        var dtoRespones = entities.stream().
                map(ResortAmenityMapper.INSTANCE::toDtoResponse).toList();
        return dtoRespones;
    }

    @Override
    public List<ResortAmenityResponse> gets(Long amenityTypeId, Long resortId) {
        var entities = resortAmenityRepository.findAllByResortAmenityTypeIdAndResortId(amenityTypeId, resortId);

        var dtoRespones = entities.stream().
                map(ResortAmenityMapper.INSTANCE::toDtoResponse).toList();
        return dtoRespones;
    }

    @Override
    public ResortAmenityResponse get(Long id) {
        var entity = resortAmenityRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(RESORT_AMENITY_NOT_FOUND));
        var dtoReponse = ResortAmenityMapper.INSTANCE.toDtoResponse(entity);
        return dtoReponse;
    }

    @Override
    public ResortAmenityResponse create(ResortAmenityRequest dtoRequest, MultipartFile resortAmenityIcon) {
        if (resortAmenityRepository.findByResortAmenityNameEqualsIgnoreCaseAndIsDeletedIsFalse
                (dtoRequest.getResortAmenityName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_RESORT_AMENITY);

        if (resortAmenityTypeRepository.findByIdAndIsDeletedFalse(dtoRequest.getResortAmenityTypeId()).isEmpty())
            throw new DataIntegrityViolationException(RESORT_AMENITY_TYPE_DELETED);

        String link = null;
        try {
            link = fileService.uploadFile(resortAmenityIcon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var entity = ResortAmenityMapper.INSTANCE.toEntity(dtoRequest);
        entity.setResortAmenityLinkIcon(link);
        entity.setIsDeleted(false);
        var dtoResponse = ResortAmenityMapper.INSTANCE.toDtoResponse(resortAmenityRepository.save(entity));
        return dtoResponse;
    }

    @Override
    public ResortAmenityResponse update(Long id, ResortAmenityRequest dtoRequest, MultipartFile resortAmenityIcon) {
        var entity = resortAmenityRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(RESORT_AMENITY_NOT_FOUND));

        var entityFound = resortAmenityRepository.findByResortAmenityNameEqualsIgnoreCaseAndIsDeletedIsFalse(dtoRequest.getResortAmenityName());

        if (entityFound.isPresent() && !Objects.equals(entityFound.get().getId(), id))
            throw new DuplicateRecordException(DUPLICATE_RESORT_AMENITY);

        if (resortAmenityTypeRepository.findByIdAndIsDeletedFalse(dtoRequest.getResortAmenityTypeId()).isEmpty())
            throw new DataIntegrityViolationException(RESORT_AMENITY_TYPE_DELETED);

        ResortAmenityMapper.INSTANCE.updateEntityFromDTO(dtoRequest, entity);
        if (resortAmenityIcon != null) {
            String link = null;
            try {
                link = fileService.uploadFile(resortAmenityIcon);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            entity.setResortAmenityLinkIcon(link);
        }
         resortAmenityRepository.save(entity);
        var dtoResponse = ResortAmenityMapper.INSTANCE.toDtoResponse(entity);
        return dtoResponse;
    }

    @Override
    public void delete(Long id) {
        var entity = resortAmenityRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(RESORT_AMENITY_NOT_FOUND));
        entity.setIsDeleted(true);
        resortAmenityRepository.save(entity);
    }
}
