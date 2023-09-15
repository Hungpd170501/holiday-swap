package com.example.holidayswap.service.property.inRoomAmenityService;

import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.InRoomAmenityRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityResponse;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenity;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.inRoomAmenity.InRoomAmenityMapper;
import com.example.holidayswap.repository.property.inRoomAmenity.InRoomAmenityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.IN_ROOM_AMENITY;

@Service
@RequiredArgsConstructor
public class InRoomAmenityServiceImpl implements InRoomAmenityService {
    private final InRoomAmenityRepository inRoomAmenityRepository;
    private final InRoomAmenityMapper inRoomAmenityMapper;

    @Override
    public Page<InRoomAmenityResponse> gets(String name, Pageable pageable) {
        Page<InRoomAmenity> inRoomAmenityPage = inRoomAmenityRepository.
                findInRoomAmenitiesByInRoomAmenitiesNameAndIsDeletedIsFalse(name, pageable);
        Page<InRoomAmenityResponse> inRoomAmenityResponsePage = inRoomAmenityPage.map(inRoomAmenityMapper::toInRoomAmenityResponse);
        return inRoomAmenityResponsePage;
    }

    @Override
    public InRoomAmenityResponse get(Long id) {
        var inRoomAmenityFound = inRoomAmenityRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY));
        var inRoomAmenityResponse = inRoomAmenityMapper.toInRoomAmenityResponse(inRoomAmenityFound);
        return inRoomAmenityResponse;
    }

    @Override
    public InRoomAmenityResponse create(InRoomAmenityRequest inRoomAmenityRequest) {
        var inRoomAmenity = inRoomAmenityMapper.toInRoomAmenity(inRoomAmenityRequest);
        var inRoomAmenityNew = inRoomAmenityRepository.save(inRoomAmenity);
        return inRoomAmenityMapper.toInRoomAmenityResponse(inRoomAmenityNew);
    }

    @Override
    public InRoomAmenityResponse update(Long id, InRoomAmenityRequest inRoomAmenityRequest) {
        var inRoomAmenityFound = inRoomAmenityRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY));
        inRoomAmenityMapper.updateEntityFromDTO(inRoomAmenityRequest, inRoomAmenityFound);
        inRoomAmenityRepository.save(inRoomAmenityFound);
        return inRoomAmenityMapper.toInRoomAmenityResponse(inRoomAmenityFound);
    }

    @Override
    public void delete(Long id) {
        var inRoomAmenityFound = inRoomAmenityRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY));
        inRoomAmenityFound.setIsDeleted(true);
        inRoomAmenityRepository.save(inRoomAmenityFound);
    }
}
