package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.request.property.coOwner.ContractImageRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.coOwner.ContractType;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.coOwner.CoOwnerMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.service.property.timeFame.TimeFrameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class CoOwnerServiceImpl implements CoOwnerService {
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final CoOwnerRepository coOwnerRepository;
    private final ContractImageService contractImageService;
    private final TimeFrameService timeFrameService;

    @Override
    public List<CoOwnerResponse> getListByPropertyId(Long propertyId) {
        var dtoResponse = coOwnerRepository.findAllByPropertyIdAndIsDeletedIsFalse(propertyId).
                stream().map(CoOwnerMapper.INSTANCE::toDtoResponse).toList();
        return dtoResponse;
    }

    @Override
    public List<CoOwnerResponse> getListByUserId(Long userId) {
        var dtoResponse = coOwnerRepository.findAllByUserIdAndIsDeletedIsFalse(userId).
                stream().map(CoOwnerMapper.INSTANCE::toDtoResponse).toList();
        return dtoResponse;
    }

    @Override
    public CoOwnerResponse get(Long propertyId, Long userId, String roomId) {
        var dtoResponse = CoOwnerMapper.INSTANCE.toDtoResponse(
                coOwnerRepository.findByPropertyIdAndUserUserIdAndIdRoomId(propertyId, userId, roomId).
                        orElseThrow(() -> new EntityNotFoundException(OWNERSHIP_NOT_FOUND)));
        dtoResponse.setContractImages(contractImageService.gets(
                dtoResponse.getId().getPropertyId(),
                dtoResponse.getId().getUserId(),
                dtoResponse.getId().getRoomId()));
        return dtoResponse;
    }

    @Override
    @Transactional
    public CoOwnerResponse create(Long propertyId,
                                  Long userId,
                                  String roomId,
                                  CoOwnerRequest dtoRequest) {
        if (dtoRequest.getType() == ContractType.DEEDED) {
            dtoRequest.setStartTime(null);
            dtoRequest.setEndTime(null);
        } else if (dtoRequest.getStartTime().after(dtoRequest.getEndTime()))
            throw new DataIntegrityViolationException("Start time must be before end time");
        var entity = CoOwnerMapper.INSTANCE.toEntity(dtoRequest);
        var property = propertyRepository.findPropertyByIdAndIsDeletedIsFalse(propertyId).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND));

        var id = new CoOwnerId();
        id.setPropertyId(propertyId);
        id.setUserId(userId);
        id.setRoomId(roomId);

        entity.setId(id);
        entity.setProperty(property);
        entity.setUser(user);
        entity.setStatus(CoOwnerStatus.PENDING);
        var created = coOwnerRepository.save(entity);
        dtoRequest.getTimeFrames().forEach(e -> {
            timeFrameService.create(id, e);
        });
        return CoOwnerMapper.INSTANCE.toDtoResponse(created);
    }

    @Override
    @Transactional
    public CoOwnerResponse create(Long propertyId,
                                  Long userId,
                                  String roomId,
                                  CoOwnerRequest dtoRequest,
                                  List<MultipartFile> contractImages) {
        var dtoResponse = create(propertyId, userId, roomId, dtoRequest);
        contractImages.forEach(e -> {
            ContractImageRequest id = new ContractImageRequest();
            id.setPropertyId(propertyId);
            id.setUserId(userId);
            id.setRoomId(roomId);
            contractImageService.create(id, e);
        });
        return dtoResponse;
    }

    @Override
    public CoOwnerResponse update(Long propertyId, Long userId, String roomId, CoOwnerStatus coOwnerStatus) {
        var entity = coOwnerRepository.findAllByPropertyIdAndUserIdAndRoomIdAndIsDeleteIsFalse(propertyId, userId, roomId).
                orElseThrow(() -> new EntityNotFoundException(OWNERSHIP_NOT_FOUND));
        entity.setStatus(coOwnerStatus);
        var updated = coOwnerRepository.save(entity);
        return CoOwnerMapper.INSTANCE.toDtoResponse(updated);
    }

    @Override
    public void delete(Long propertyId, Long userId, String roomId) {
        var entity = coOwnerRepository.findAllByPropertyIdAndUserIdAndRoomIdAndIsDeleteIsFalse(propertyId, userId, roomId).
                orElseThrow(() -> new EntityNotFoundException(OWNERSHIP_NOT_FOUND));
        entity.setDeleted(true);
        coOwnerRepository.save(entity);
    }
}
