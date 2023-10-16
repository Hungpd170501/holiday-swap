package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
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
    private final CoOwnerMapper coOwnerMapper;

    @Override
    public Page<CoOwnerResponse> gets(Long resortId, Long propertyId, Long userId, String roomId, CoOwnerStatus coOwnerStatus, Pageable pageable) {
        String status = null;
        if (coOwnerStatus != null) status = coOwnerStatus.toString();
        var entities = coOwnerRepository.findAllByResortIdPropertyIdAndUserIdAndRoomId(
                        resortId, propertyId, userId, roomId, status, pageable).
                map(coOwnerMapper::toDtoResponse);
        return entities;
    }

    @Override
    public CoOwnerResponse get(Long propertyId, Long userId, String roomId) {
        var dtoResponse = CoOwnerMapper.INSTANCE.toDtoResponse(
                coOwnerRepository.findByPropertyIdAndUserIdAndIdRoomId(propertyId, userId, roomId).
                        orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND)));
        dtoResponse.setContractImages(contractImageService.gets(
                dtoResponse.getId().getPropertyId(),
                dtoResponse.getId().getUserId(),
                dtoResponse.getId().getRoomId()));
        return dtoResponse;
    }

    @Override
    @Transactional
    public CoOwnerResponse create(CoOwnerId coOwnerId,
                                  CoOwnerRequest dtoRequest) {
        if (dtoRequest.getType() == ContractType.DEEDED) {
            dtoRequest.setStartTime(null);
            dtoRequest.setEndTime(null);
        } else {
            Date currentDate = new Date();
            //start year must less than end year
            if (dtoRequest.getStartTime().getYear() >= dtoRequest.getEndTime().getYear()) {
                throw new DataIntegrityViolationException("START YEAR must less than END YEAR");
            }
//            year must equals or greater than year now
            if (dtoRequest.getStartTime().getYear() < currentDate.getYear() ||
                dtoRequest.getEndTime().getYear() < currentDate.getYear()) {
                throw new DataIntegrityViolationException("YEAR INPUT must greater than YEAR NOW");
            }
        }
        var entity = CoOwnerMapper.INSTANCE.toEntity(dtoRequest);
        var property = propertyRepository.findPropertyByIdAndIsDeletedIsFalseAndStatus(coOwnerId.getPropertyId(), PropertyStatus.ACTIVE).
                orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var user = userRepository.findById(coOwnerId.getUserId()).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND));
        entity.setId(coOwnerId);
        entity.setProperty(property);
        entity.setUser(user);
        entity.setStatus(CoOwnerStatus.PENDING);
        var created = coOwnerRepository.save(entity);
        dtoRequest.getTimeFrames().forEach(e -> {
            timeFrameService.create(coOwnerId, e);
        });
        return CoOwnerMapper.INSTANCE.toDtoResponse(created);
    }

    @Override
    @Transactional
    public CoOwnerResponse create(CoOwnerId coOwnerId,
                                  CoOwnerRequest dtoRequest,
                                  List<MultipartFile> contractImages) {
        var dtoResponse = create(coOwnerId, dtoRequest);
        contractImages.forEach(e -> {
            //ContractImageRequest id = new ContractImageRequest();
            contractImageService.create(coOwnerId, e);
        });
        return dtoResponse;
    }

    //Accept the register of Owner
    @Override
    public CoOwnerResponse update(CoOwnerId coOwnerId, CoOwnerStatus coOwnerStatus) {
        var entity = coOwnerRepository.findAllByPropertyIdAndUserIdAndRoomIdAndIsDeleteIsFalse(coOwnerId.getPropertyId(), coOwnerId.getUserId(), coOwnerId.getRoomId()).
                orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND));
        entity.setStatus(coOwnerStatus);
        var updated = coOwnerRepository.save(entity);
        return CoOwnerMapper.INSTANCE.toDtoResponse(updated);
    }

    @Override
    public void delete(CoOwnerId coOwnerId) {
        var entity = coOwnerRepository.
                findAllByPropertyIdAndUserIdAndRoomIdAndIsDeleteIsFalse(
                        coOwnerId.getPropertyId(), coOwnerId.getUserId(), coOwnerId.getRoomId()).
                orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND));
        entity.setDeleted(true);
        coOwnerRepository.save(entity);
    }
}
