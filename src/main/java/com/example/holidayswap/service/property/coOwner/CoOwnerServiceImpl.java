package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerMaintenanceStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.coOwner.ContractType;
import com.example.holidayswap.domain.entity.property.coOwner.OwnerShipMaintenance;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.coOwner.CoOwnerMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerMaintenanceRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.repository.resort.ResortRepository;
import com.example.holidayswap.service.EmailService;
import com.example.holidayswap.service.auth.UserService;
import com.example.holidayswap.service.booking.IBookingService;
import com.example.holidayswap.service.notification.PushNotificationService;
import com.example.holidayswap.service.property.timeFame.TimeFrameService;
import com.example.holidayswap.service.resort.ResortService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoOwnerServiceImpl implements CoOwnerService {
    private final CoOwnerMaintenanceRepository coOwnerMaintenanceRepository;

    private final CoOwnerRepository coOwnerRepository;

    private final ResortService resortService;
    private final TimeFrameService timeFrameService;
    private final UserService userService;
    private final ContractImageService contractImageService;
    private final PushNotificationService pushNotificationService;
    private final EmailService emailService;
    private final IBookingService bookingService;

    private final IOwnerShipMaintenanceService ownerShipMaintenanceService;

    private final PropertyRepository propertyRepository;
    private final ResortRepository resortRepository;
    private final UserRepository userRepository;

    @Override
    public Page<CoOwnerResponse> gets(Long resortId, Long propertyId, Long userId, String roomId, CoOwnerStatus coOwnerStatus, Pageable pageable) {
        String status = null;
        if (coOwnerStatus != null) status = coOwnerStatus.toString();
        var entities = coOwnerRepository.findAllByResortIdAndPropertyIdAndUserIdAndRoomIdAndStatus(resortId, propertyId, userId, roomId, status, pageable).map(CoOwnerMapper.INSTANCE::toDtoResponse);
        return entities;
    }

    @Override
    public CoOwnerResponse get(Long coOwnerId) {
        var coOwner = coOwnerRepository.findById(coOwnerId).orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND));
        coOwner.setAvailableTimes(coOwner.getAvailableTimes().stream().filter(a -> !a.isDeleted() && a.getStatus() == AvailableTimeStatus.OPEN).toList());
        coOwner.setContractImages(coOwner.getContractImages().stream().filter(e -> !e.getIsDeleted()).toList());
        var rs = CoOwnerMapper.INSTANCE.toDtoResponse(coOwner);
//        var resort = resortService.get(coOwner.getProperty().getResortId());
//        var listTimeFrame = timeFrameRepository.findAllByPropertyIdAAndUserIdAndRoomId(propertyId, userId, roomId);
//        List<TimeFrameResponse> listTimeFrameRsp = listTimeFrame.stream().map(TimeFrameMapper.INSTANCE::toDtoResponse).toList();
//        dtoResponse.setTimeFrames(listTimeFrameRsp);
        return rs;
    }

    void compareTwoDate(LocalDate start, LocalDate end) {
        if (start.getYear() > end.getYear())
            throw new DataIntegrityViolationException("Start date can not after end date!.");
        if (end.getYear() < (LocalDate.now()).getYear())
            throw new DataIntegrityViolationException("End date must after current date!.");
    }

    void checkValid(Long proId, Long userId) {
        var pro = propertyRepository.findById(proId).orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var res = resortRepository.findById(pro.getResortId()).orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        if (pro.getIsDeleted()) throw new DataIntegrityViolationException("Apartment already deleted!.");
        if (pro.getStatus() != PropertyStatus.ACTIVE)
            throw new DataIntegrityViolationException("Apartment is not valid!.");
        if (res.isDeleted()) throw new DataIntegrityViolationException("Resort already deleted!.");
        if (res.getStatus() != ResortStatus.ACTIVE) throw new DataIntegrityViolationException("Resort is not valid!.");
        if (user.getStatus() != UserStatus.ACTIVE) throw new DataIntegrityViolationException("User is not valid!.");
    }

    void checkValidNextUse(LocalDate start, Set<Integer> timeFrames) {
        if (start.getYear() < LocalDate.now().getYear())
            throw new DataIntegrityViolationException("Next use year can not less than current year!.");
//        if (start.getYear() == LocalDate.now().getYear()) {
//            int currentWeekIso = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
//            timeFrames.forEach(w -> {
//                if (w < currentWeekIso)
//                    throw new DataIntegrityViolationException("Week input is out of week current date!.");
//            });
//        }
    }

    @Override
    @Transactional
    public void create(CoOwnerRequest dtoRequest) {
        checkValid(dtoRequest.getPropertyId(), dtoRequest.getUserId());
        //Deeded
        if (dtoRequest.getType() == ContractType.DEEDED) {
            checkValidNextUse(dtoRequest.getStartTime(), dtoRequest.getTimeFrames());
            dtoRequest.setEndTime(null);
        }
        //Right to use
        else if (dtoRequest.getType() == ContractType.RIGHT_TO_USE) {
            compareTwoDate(dtoRequest.getStartTime(), dtoRequest.getEndTime());
        }
        var newCoOwner = CoOwnerMapper.INSTANCE.toEntity(dtoRequest);
        newCoOwner.setStatus(CoOwnerStatus.PENDING);
        newCoOwner.setCreateDate(new Date());
        var co = coOwnerRepository.save(newCoOwner);
        dtoRequest.getTimeFrames().forEach((wN -> {
            timeFrameService.create(co.getId(), wN);
        }));
    }

    @Override
    @Transactional
    public void create(CoOwnerRequest dtoRequest, List<MultipartFile> propertyImages) {
        checkValid(dtoRequest.getPropertyId(), dtoRequest.getUserId());
        //Deeded
        if (dtoRequest.getType() == ContractType.DEEDED) {
            checkValidNextUse(dtoRequest.getStartTime(), dtoRequest.getTimeFrames());
            dtoRequest.setEndTime(null);
        }
        //Right to use
        else if (dtoRequest.getType() == ContractType.RIGHT_TO_USE) {
            compareTwoDate(dtoRequest.getStartTime(), dtoRequest.getEndTime());
        }
        var newCoOwner = CoOwnerMapper.INSTANCE.toEntity(dtoRequest);
        newCoOwner.setStatus(CoOwnerStatus.PENDING);
        newCoOwner.setCreateDate(new Date());
        var co = coOwnerRepository.save(newCoOwner);
        dtoRequest.getTimeFrames().forEach((wN -> {
            timeFrameService.create(co.getId(), wN);
        }));
        propertyImages.forEach(img -> {
            contractImageService.create(co.getId(), img);
        });
    }

    @Override
    public void update(Long coOwnerId, CoOwnerStatus coOwnerStatus) {
        var notification = new NotificationRequest();
        var co = coOwnerRepository.findById(coOwnerId).orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND));
        var coIsCreatedBf = coOwnerRepository.findByPropertyIdAndUserIdAndRoomIdAndType(co.getPropertyId(), co.getUserId(), co.getRoomId(), co.getType().toString(), co.getStartTime(), co.getEndTime());
        if (coIsCreatedBf.isPresent() && !coIsCreatedBf.get().getId().equals(co.getId())) {
            //Append to co
            co.getTimeFrames().forEach(tf -> {
                timeFrameService.appendToCo(coIsCreatedBf.get().getId(), tf);
            });
            co.getContractImages().forEach(image -> {
                contractImageService.appendToCo(coIsCreatedBf.get().getId(), image);
            });
            deleteHard(coOwnerId);
            coIsCreatedBf.get().setCreateDate(new Date());
            coOwnerRepository.save(coIsCreatedBf.get());
        } else {
            if (co.getStatus() != CoOwnerStatus.PENDING)
                throw new DataIntegrityViolationException("Can not perform this action!.");
            co.setStatus(coOwnerStatus);

            if (coOwnerStatus == CoOwnerStatus.ACCEPTED) {
                co.getTimeFrames().forEach((tf) -> {
                    timeFrameService.update(tf.getId(), coOwnerStatus);
                });
                //update role user to Membership
                var user = userRepository.findById(co.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found to accept co-Owner in apartment!."));
                //send mail
                try {
                    notification.setSubject("Apartment register co-owner accepted");
                    notification.setContent("Your register co-owner in apartment " + co.getProperty().getPropertyName() + "is now accepted");
                    notification.setToUserId(co.getUserId());
                    pushNotificationService.createNotification(notification);
                    emailService.sendNotificationRegisterCoOwnerSuccessEmail(user.getEmail(), user.getUsername());
                } catch (Exception e) {
                    log.error("Error sending verification email", e);
                }
            } else if (coOwnerStatus.equals(CoOwnerStatus.REJECTED)) {
                userService.upgradeUserToMember(co.getUserId());
                var user = userRepository.findById(co.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found to accept co-Owner in apartment!."));
                //send mail
                try {
                    notification.setSubject("Apartment register co-owner rejected");
                    notification.setContent("Your register co-owner in apartment " + co.getProperty().getPropertyName() + "is now rejected. Check your apartment and contract again");
                    notification.setToUserId(co.getUserId());
                    pushNotificationService.createNotification(notification);
                    emailService.sendNotificationRegisterCoOwnerDeclineEmail(user.getEmail(), user.getUsername());
                } catch (Exception e) {
                    log.error("Error sending verification email", e);
                }
            }
            co.setCreateDate(new Date());
            coOwnerRepository.save(co);
        }
    }

    @Override
    public void delete(Long coOwnerId) {
        var coOwner = coOwnerRepository.findById(coOwnerId).orElseThrow();
        coOwner.setDeleted(true);
        coOwnerRepository.save(coOwner);
    }

    @Override
    public void deleteHard(Long coOwnerId) {
        coOwnerRepository.deleteById(coOwnerId);
    }

    @Override
    public void updateStatus(Long propertyId, String apartmentId, CoOwnerMaintenanceStatus resortStatus, LocalDateTime startDate, LocalDateTime endDate, List<MultipartFile> resortImage) throws MessagingException, IOException {
        if(startDate.isBefore(LocalDateTime.now())) throw new DataIntegrityViolationException("Start date must be after today");
        if(startDate.isEqual(LocalDateTime.now())) throw new DataIntegrityViolationException("Start date must be after today");
        var entity = coOwnerRepository.findByPropertyIdAndRoomId(propertyId,apartmentId);

        List<String> listImage = ownerShipMaintenanceService.CreateOwnerShipMaintenance(propertyId,apartmentId, startDate, endDate, resortStatus, resortImage);
        if(entity.size() >0){
            bookingService.deactiveApartmentNotifyBookingUser(propertyId,apartmentId, startDate, endDate, resortStatus, listImage);
        }
    }

    @Override
    public List<OwnerShipMaintenance> getListOwnerShipMaintenance(Long propertyId, String apartmentId) {
        return coOwnerMaintenanceRepository.findByPropertyIdAndApartmentId(propertyId, apartmentId).stream().toList();
    }
}
