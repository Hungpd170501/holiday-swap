package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.entity.resort.Resort;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.resort.ResortMapper;
import com.example.holidayswap.repository.resort.ResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.DUPLICATE_RESORT_NAME;
import static com.example.holidayswap.constants.ErrorMessage.RESORT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ResortServiceImpl implements ResortService {
    private final ResortRepository resortRepository;

    @Override
    public Page<ResortResponse> gets(String name, Pageable pageable) {
        Page<Resort> inRoomAmenityTypePage = resortRepository.
                findAllByResortNameContainingIgnoreCaseAndDeletedFalse(name, pageable);
        return inRoomAmenityTypePage.map(ResortMapper.INSTANCE::toResortResponse);
    }

    @Override
    public ResortResponse get(Long id) {
        var entity = resortRepository.findByIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        return ResortMapper.INSTANCE.toResortResponse(entity);
    }

    @Override
    public ResortResponse create(ResortRequest resortRequest) {
        if (resortRepository.findByResortNameContainingIgnoreCaseAndDeletedFalse(resortRequest.getResortName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_RESORT_NAME);
        var entity = ResortMapper.INSTANCE.toResort(resortRequest);
        return ResortMapper.INSTANCE.toResortResponse(resortRepository.save(entity));
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
