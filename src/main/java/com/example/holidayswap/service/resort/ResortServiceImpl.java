package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.entity.resort.Resort;
import com.example.holidayswap.domain.entity.resort.amentity.ResortAmenity;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.resort.ResortMapper;
import com.example.holidayswap.repository.resort.ResortRepository;
import com.example.holidayswap.repository.resort.amenity.ResortAmenityRepository;
import com.example.holidayswap.service.resort.amenity.ResortAmenityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ResortServiceImpl implements ResortService {
    private final ResortRepository resortRepository;
    private final ResortImageService resortImageService;
    private final ResortAmenityTypeService resortAmenityTypeService;
    private final ResortAmenityRepository resortAmenityRepository;
    @Override
    public Page<ResortResponse> gets(String name, Pageable pageable) {
        Page<Resort> inRoomAmenityTypePage = resortRepository.
                findAllByResortNameContainingIgnoreCaseAndDeletedFalse(name, pageable);
        return inRoomAmenityTypePage.map(e -> {
            var dto = ResortMapper.INSTANCE.toResortResponse(e);
            dto.setResortImages(resortImageService.gets(e.getId()));
            dto.setResortAmenityTypes(resortAmenityTypeService.gets(e.getId()));
            return dto;
        });
    }

    @Override
    public ResortResponse get(Long id) {
        var entity = resortRepository.findByIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        var dtoResponse = ResortMapper.INSTANCE.toResortResponse(entity);
        dtoResponse.setResortImages(resortImageService.gets(id));
        dtoResponse.setResortAmenityTypes(resortAmenityTypeService.gets(entity.getId()));
        return dtoResponse;
    }

    @Override
    public ResortResponse create(ResortRequest resortRequest) {
        if (resortRepository.findByResortNameContainingIgnoreCaseAndDeletedFalse(resortRequest.getResortName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_RESORT_NAME);
        var entity = ResortMapper.INSTANCE.toResort(resortRequest);
        List<ResortAmenity> resortAmenities = new ArrayList<>();
        resortRequest.getResortAmentities().forEach(e -> {
            resortAmenities.add(resortAmenityRepository.findByIdAndIsDeletedFalse(e).orElseThrow(() -> new EntityNotFoundException(RESORT_AMENITY_NOT_FOUND)));
        });
        entity.setAmenities(resortAmenities);
        return ResortMapper.INSTANCE.toResortResponse(resortRepository.save(entity));
    }

    @Override
    public ResortResponse create(ResortRequest resortRequest, List<MultipartFile> resortImage) {
        var entity = create(resortRequest);
        resortImage.forEach(e -> {
            resortImageService.create(entity.getId(), e);
        });
        return get(entity.getId());
    }

    @Override
    public ResortResponse update(Long id, ResortRequest resortRequest) {
        if (resortRepository.findByResortNameContainingIgnoreCaseAndDeletedFalse(resortRequest.getResortName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_RESORT_NAME);
        var entity = resortRepository.findByIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        ResortMapper.INSTANCE.updateEntityFromDTO(resortRequest, entity);
        return ResortMapper.INSTANCE.toResortResponse(resortRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var inRoomAmenityTypeFound = resortRepository.findByIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        inRoomAmenityTypeFound.setDeleted(true);
        resortRepository.save(inRoomAmenityTypeFound);
    }
}
