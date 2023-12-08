package com.example.holidayswap.service.property.timeFame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.AvailableTimeRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.timeFrame.AvailableTimeMapper;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.repository.property.timeFrame.TimeFrameRepository;
import com.example.holidayswap.repository.resort.ResortRepository;
import com.example.holidayswap.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AvailableTimeServiceImpl implements AvailableTimeService {
    private final AvailableTimeRepository availableTimeRepository;
    private final TimeFrameRepository timeFrameRepository;
    private final AuthUtils authUtils;
    private final CoOwnerRepository coOwnerRepository;
    private final AvailableTimeMapper availableTimeMapper;
    private final BookingRepository bookingRepository;
    private final ResortRepository resortRepository;
    private final PropertyRepository propertyRepository;

    @Override
    public Page<AvailableTimeResponse> getAllByVacationUnitId(Long timeFrameId, Pageable pageable) {
        var availableTimePage = availableTimeRepository.findAllByVacationUnitIdAndDeletedIsFalse(timeFrameId, pageable);
        Page<AvailableTimeResponse> availableTimePageResponse = availableTimePage.map(AvailableTimeMapper.INSTANCE::toDtoResponse);
        return availableTimePageResponse;
    }

    @Override
    public Page<AvailableTimeResponse> getAllByPropertyId(Long propertyId, Pageable pageable) {
        var availableTimePage = availableTimeRepository.findAllByPropertyIdAndDeletedFalse(propertyId, pageable);
        Page<AvailableTimeResponse> availableTimePageResponse = availableTimePage.map(AvailableTimeMapper.INSTANCE::toDtoResponse);
        return availableTimePageResponse;
    }

    @Override
    public List<AvailableTimeResponse> getAllByTimeFrameIdAndYear(Long timeFrameId, int year) {
        var list = availableTimeRepository.findAllByTimeFrameIdAndYear(timeFrameId, year);

        return list.stream().map(availableTimeMapper::toDtoResponse).collect(Collectors.toList());
    }

    @Override
    public Page<AvailableTimeResponse> getAllByResortId(Long resortId, Pageable pageable) {
        var availableTimePage = availableTimeRepository.findAllByResortIdAndDeletedFalse(resortId, pageable);
        Page<AvailableTimeResponse> availableTimePageResponse = availableTimePage.map(AvailableTimeMapper.INSTANCE::toDtoResponse);
        return availableTimePageResponse;
    }

    @Override
    public List<AvailableTimeResponse> getAllByCoOwnerId(CoOwnerId coOwnerId) {
        var availableTimeList = availableTimeRepository.findAllByCoOwnerId(coOwnerId.getPropertyId(), coOwnerId.getUserId(), coOwnerId.getRoomId());
        List<AvailableTimeResponse> responses = availableTimeList.stream().map(availableTimeMapper::toDtoResponse).collect(Collectors.toList());
        return responses;
    }

    @Override
    public AvailableTimeResponse get(Long id) {
        var availableTimeFound = availableTimeRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(AVAILABLE_TIME_NOT_FOUND));
        var availableTimeResponse = AvailableTimeMapper.INSTANCE.toDtoResponse(availableTimeFound);
        availableTimeResponse.setTimeHasBooked(bookingRepository.findAllByTimeFrameIdAndStatus(id, EnumBookingStatus.BookingStatus.SUCCESS));
        return availableTimeResponse;
    }

    @Override
    public AvailableTimeResponse create(Long timeFrameId, AvailableTimeRequest dtoRequest) {
        Date currentDate = new Date();
        if (dtoRequest.getStartTime().after(dtoRequest.getEndTime()))
            throw new DataIntegrityViolationException("Start time must be before end time");
        if (dtoRequest.getStartTime().before(currentDate) || dtoRequest.getEndTime().before(currentDate)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            throw new DataIntegrityViolationException("Date must after " + formatter.format(currentDate));
        }
        var timeFrame = timeFrameRepository.findByIdAndIsDeletedIsFalse(timeFrameId).orElseThrow(() -> new EntityNotFoundException(TIME_FRAME_NOT_FOUND));
        authUtils.isBelongToMember(timeFrame.getUserId());
        if (timeFrame.getStatus() == TimeFrameStatus.PENDING)
            throw new DataIntegrityViolationException("TIME-FRAME is not accepted. Please contact to staff.");
        //check proeprty
        var property = propertyRepository.findById(timeFrame.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Apartment not found"));
        if (property.getIsDeleted())
            throw new DataIntegrityViolationException("Apartment is deleted!. Please contact to Holiday Swap to more information!.");
        if (property.getStatus() != PropertyStatus.ACTIVE)
            throw new DataIntegrityViolationException("Apartment is DEACTIVATE!. Please contact to Holiday Swap to more information!.");
        //check resort
        var resort = resortRepository.findById(property.getResortId()).orElseThrow(() -> new EntityNotFoundException("Resort not found"));
        if (resort.isDeleted())
            throw new DataIntegrityViolationException("Resort is deleted!. Please contact to Holiday Swap to more information!.");
        if (resort.getStatus() != ResortStatus.ACTIVE)
            throw new DataIntegrityViolationException("Resort is DEACTIVATE!. Please contact to Holiday Swap to more information!.");
        //check is in Time-frame
        var isInTimeFrame = timeFrameRepository.isMatchingTimeFrames(timeFrameId, dtoRequest.getStartTime(), dtoRequest.getEndTime(), TimeFrameStatus.ACCEPTED.name()).orElseThrow(() -> new EntityNotFoundException("Date input is not in range of timeframe"));
        var isInCoOwnerTime = coOwnerRepository.isMatchingCoOwner(isInTimeFrame.getPropertyId(), isInTimeFrame.getUserId(), isInTimeFrame.getRoomId(), dtoRequest.getStartTime(), dtoRequest.getEndTime(), CoOwnerStatus.ACCEPTED.name()).orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND));
        var checkDuplicateWhichAny = availableTimeRepository.findOverlapsWhichAnyTimeDeposit(timeFrameId, dtoRequest.getStartTime(), dtoRequest.getEndTime(), AvailableTimeStatus.OPEN.name());
        if (!checkDuplicateWhichAny.isEmpty())
            throw new DataIntegrityViolationException("Duplicate with another time!.");
        //check if have any booking on its time.
        var checkIsHaveAnyBookingYet = bookingRepository.checkTimeFrameIsHaveAnyBookingYetInTheTimeYet(dtoRequest.getStartTime(), dtoRequest.getEndTime(), timeFrameId);
        if (!checkIsHaveAnyBookingYet.isEmpty())
            throw new DataIntegrityViolationException("This time have a booking already, Please create in another date");
        var availableTime = AvailableTimeMapper.INSTANCE.toEntity(dtoRequest);
        availableTime.setStatus(AvailableTimeStatus.OPEN);
        availableTime.setTimeFrameId(timeFrameId);
        var availableTimeCreated = availableTimeRepository.save(availableTime);
        return AvailableTimeMapper.INSTANCE.toDtoResponse(availableTimeCreated);
    }

    @Override
    public AvailableTimeResponse update(Long id, AvailableTimeRequest availableTimeRequest) {
        var availableTime = availableTimeRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(AVAILABLE_TIME_NOT_FOUND));
        AvailableTimeMapper.INSTANCE.updateEntityFromDTO(availableTimeRequest, availableTime);
        var availableTimeCreated = availableTimeRepository.save(availableTime);
        return AvailableTimeMapper.INSTANCE.toDtoResponse(availableTimeCreated);
    }

    @Override
    public AvailableTimeResponse update(Long id, AvailableTimeStatus availableTimeStatus) {
        var availableTime = availableTimeRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(AVAILABLE_TIME_NOT_FOUND));
        availableTime.setStatus(availableTimeStatus);
        var availableTimeCreated = availableTimeRepository.save(availableTime);
        return AvailableTimeMapper.INSTANCE.toDtoResponse(availableTime);
    }

    @Override
    public void delete(Long id) {
        var availableTime = availableTimeRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(AVAILABLE_TIME_NOT_FOUND));
        availableTime.setDeleted(true);
        availableTimeRepository.save(availableTime);
    }
}
