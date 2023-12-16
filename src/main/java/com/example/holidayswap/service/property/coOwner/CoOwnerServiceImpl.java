package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.coOwner.ContractType;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.coOwner.CoOwnerMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.repository.resort.ResortRepository;
import com.example.holidayswap.service.EmailService;
import com.example.holidayswap.service.auth.UserService;
import com.example.holidayswap.service.notification.PushNotificationService;
import com.example.holidayswap.service.property.PropertyService;
import com.example.holidayswap.service.property.timeFame.TimeFrameService;
import com.example.holidayswap.service.resort.ResortService;
import com.example.holidayswap.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoOwnerServiceImpl implements CoOwnerService {
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final CoOwnerRepository coOwnerRepository;
    private final ContractImageService contractImageService;
    private final TimeFrameService timeFrameService;
    private final UserService userService;
    private final ResortRepository resortRepository;
    private final EmailService emailService;
    private final ResortService resortService;
    private final PropertyService propertyService;
    private final AuthUtils authUtils;
    private final PushNotificationService pushNotificationService;

    @Override
    public Page<CoOwnerResponse> gets(Long resortId, Long propertyId, Long userId, String roomId, CoOwnerStatus coOwnerStatus, Pageable pageable) {
        String status = null;
        String propertyStatus = null;
        String resortStatus = null;
        var user = authUtils.GetUser();
        if (user.isPresent()) {
            if (user.get().getRole().getRoleId() == 4) {
                propertyStatus = PropertyStatus.ACTIVE.toString();
                resortStatus = ResortStatus.ACTIVE.toString();
            }
        }
        if (coOwnerStatus != null) status = coOwnerStatus.toString();
        var entities = coOwnerRepository.findAllByResortIdPropertyIdAndUserIdAndRoomId(resortId, propertyId, userId, roomId, status, propertyStatus, resortStatus, pageable).map(CoOwnerMapper.INSTANCE::toDtoResponse);
        return entities;
    }

    @Override
    public CoOwnerResponse get(Long propertyId, Long userId, String roomId) {
        var dtoResponse = CoOwnerMapper.INSTANCE.toDtoResponse(coOwnerRepository.findByPropertyIdAndUserIdAndIdRoomId(propertyId, userId, roomId).orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND)));
        dtoResponse.setContractImages(contractImageService.gets(dtoResponse.getId().getPropertyId(), dtoResponse.getId().getUserId(), dtoResponse.getId().getRoomId()));
        var property = propertyService.get(propertyId);
        var resort = resortService.get(property.getResortId());
        dtoResponse.setResort(resort);
        return dtoResponse;
    }

    @Override
    @Transactional
    public CoOwnerResponse create(CoOwnerId coOwnerId, CoOwnerRequest dtoRequest) {
        if (dtoRequest.getTimeFrames() == null) throw new DataIntegrityViolationException("Week number is required!.");
        if (dtoRequest.getTimeFrames().isEmpty())
            throw new DataIntegrityViolationException("Must have 1 week number input!.");
        if (dtoRequest.getType() == ContractType.DEEDED) {
            dtoRequest.setStartTime(null);
            dtoRequest.setEndTime(null);
        } else {
            Date currentDate = new Date();
            //start year must less than end year
            if (dtoRequest.getStartTime().getYear() >= dtoRequest.getEndTime().getYear()) {
                throw new DataIntegrityViolationException("START YEAR Can not greater than END YEAR");
            }
//            year must equals or greater than year now
            if (dtoRequest.getEndTime().getYear() < currentDate.getYear()) {
                throw new DataIntegrityViolationException("YEAR must greater than YEAR NOW");
            }
        }
        var entity = CoOwnerMapper.INSTANCE.toEntity(dtoRequest);
        var property = propertyRepository.findPropertyByIdAndIsDeletedIsFalseAndStatus(coOwnerId.getPropertyId(), PropertyStatus.ACTIVE).orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var resort = resortRepository.findByIdAndDeletedFalseAndResortStatus(property.getResortId(), ResortStatus.ACTIVE).orElseThrow(() -> new EntityNotFoundException("Resort not found"));
        var user = userRepository.findById(coOwnerId.getUserId()).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        entity.setId(coOwnerId);
        entity.setProperty(property);
        entity.setUser(user);
        entity.setStatus(CoOwnerStatus.PENDING);
        Date currentDate = new Date();
        entity.setCreateDate(currentDate);
        var created = coOwnerRepository.save(entity);
        dtoRequest.getTimeFrames().forEach(e -> {
            timeFrameService.create(coOwnerId, e);
        });
        return CoOwnerMapper.INSTANCE.toDtoResponse(created);
    }

    @Override
    @Transactional
    public CoOwnerResponse create(CoOwnerId coOwnerId, CoOwnerRequest dtoRequest, List<MultipartFile> contractImages) {
        var dtoResponse = create(coOwnerId, dtoRequest);
        contractImageService.deleteAll(coOwnerId);
        contractImages.forEach(e -> {
            //ContractImageRequest id = new ContractImageRequest();
            contractImageService.create(coOwnerId, e);
        });
        return dtoResponse;
    }

    /*Accept or reject the register of Owner
    from PENDING to ACCEPTED
    or PENDING to REJECTED
    can change ACCEPTED|REJECTED to another
    delete can perform
    exp: R
     */
    @Override
    @Transactional
    public CoOwnerResponse update(CoOwnerId coOwnerId, CoOwnerStatus coOwnerStatus) {
        var notification= new NotificationRequest();
        TimeFrameStatus timeFrameStatus;
        if (coOwnerStatus.equals(CoOwnerStatus.ACCEPTED)) timeFrameStatus = TimeFrameStatus.ACCEPTED;
        else if (coOwnerStatus.equals(CoOwnerStatus.REJECTED)) timeFrameStatus = TimeFrameStatus.REJECTED;
        else if (coOwnerStatus.equals(CoOwnerStatus.PENDING))
            throw new DataIntegrityViolationException("Can not perform this action. Can not change status to PENDING");
        else {
            timeFrameStatus = null;
        }
//        else timeFrameStatus = TimeFrameStatus.PENDING;
        var entity = coOwnerRepository.findAllByPropertyIdAndUserIdAndRoomIdAndIsDeleteIsFalse(coOwnerId.getPropertyId(), coOwnerId.getUserId(), coOwnerId.getRoomId()).orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND));
        if (!entity.getStatus().equals(CoOwnerStatus.PENDING))
            throw new DataIntegrityViolationException("Only change from PENDING TO ANOTHER");
        entity.setStatus(coOwnerStatus);
        entity.getTimeFrames().forEach(e -> {
            if (e.getStatus() == TimeFrameStatus.PENDING) {
                timeFrameService.update(e.getId(), timeFrameStatus);
            }
        });
        var updated = coOwnerRepository.save(entity);
        //user to membeship
        if (coOwnerStatus.equals(CoOwnerStatus.ACCEPTED)) {
            userService.upgradeUserToMember(coOwnerId.getUserId());
            var user = userRepository.findById(coOwnerId.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found to accept co-Owner in apartment!."));
            //send mail
            try {
                notification.setSubject("Apartment register co-owner accepted");
                notification.setContent("Your register co-owner in apartment " + entity.getProperty().getPropertyName() + "is now accepted");
                notification.setToUserId(coOwnerId.getUserId());
                pushNotificationService.createNotification(notification);
                emailService.sendNotificationRegisterCoOwnerSuccessEmail(user.getEmail(), user.getUsername());
            } catch (Exception e) {
                log.error("Error sending verification email", e);
            }
        } else if (coOwnerStatus.equals(CoOwnerStatus.REJECTED)) {
            userService.upgradeUserToMember(coOwnerId.getUserId());
            var user = userRepository.findById(coOwnerId.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found to accept co-Owner in apartment!."));
            //send mail
            try {
                notification.setSubject("Apartment register co-owner rejected");
                notification.setContent("Your register co-owner in apartment " + entity.getProperty().getPropertyName() + "is now rejected. Check your apartment and contract again");
                notification.setToUserId(coOwnerId.getUserId());
                pushNotificationService.createNotification(notification);
                emailService.sendNotificationRegisterCoOwnerDeclineEmail(user.getEmail(), user.getUsername());
            } catch (Exception e) {
                log.error("Error sending verification email", e);
            }

        }

        return CoOwnerMapper.INSTANCE.toDtoResponse(updated);
    }

    @Override
    public void delete(CoOwnerId coOwnerId) {
        var entity = coOwnerRepository.findAllByPropertyIdAndUserIdAndRoomIdAndIsDeleteIsFalse(coOwnerId.getPropertyId(), coOwnerId.getUserId(), coOwnerId.getRoomId()).orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND));
        entity.setDeleted(true);
        coOwnerRepository.save(entity);
    }
}
