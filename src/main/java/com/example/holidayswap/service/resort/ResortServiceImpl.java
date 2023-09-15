package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.dto.request.auth.resort.ResortRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.entity.property.Resort;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.resort.ResortMapper;
import com.example.holidayswap.repository.resort.ResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.RESORT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ResortServiceImpl implements ResortService {
    private final ResortRepository resortRepository;
    private final ResortMapper resortMapper;

    @Override
    public Page<ResortResponse> gets(Pageable pageable) {
        Page<Resort> inRoomAmenityTypePage = resortRepository.
                findAll(pageable);
        Page<ResortResponse> resortResponsePage = inRoomAmenityTypePage.map(resortMapper::toResortResponse);
        return resortResponsePage;
    }

    @Override
    public ResortResponse get(Long id) {
        var inRoomAmenityTypeFound = resortRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        var inRoomAmenityTypeResponse = resortMapper.toResortResponse(inRoomAmenityTypeFound);
        return inRoomAmenityTypeResponse;
    }

    @Override
    public ResortResponse create(ResortRequest resortRequest) {
        var inRoomAmenityType = resortMapper.toResort(resortRequest);
        var inRoomAmenityTypeNew = resortRepository.save(inRoomAmenityType);
        return resortMapper.toResortResponse(inRoomAmenityTypeNew);
    }

    @Override
    public ResortResponse update(Long id, ResortRequest resortRequest) {
        var inRoomAmenityTypeFound = resortRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        resortMapper.updateEntityFromDTO(resortRequest, inRoomAmenityTypeFound);
        resortRepository.save(inRoomAmenityTypeFound);
        return resortMapper.toResortResponse(inRoomAmenityTypeFound);
    }

    @Override
    public void delete(Long id) {
        var inRoomAmenityTypeFound = resortRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        inRoomAmenityTypeFound.setDeleted(true);
        resortRepository.save(inRoomAmenityTypeFound);
    }
}
