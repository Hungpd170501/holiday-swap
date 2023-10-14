package com.example.holidayswap.service.property.timeFame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.AvailableTimeRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.timeFrame.AvailableTimeMapper;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.repository.property.timeFrame.TimeFrameRepository;
import com.example.holidayswap.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.AVAILABLE_TIME_NOT_FOUND;
import static com.example.holidayswap.constants.ErrorMessage.TIME_FRAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AvailableTimeServiceImpl implements AvailableTimeService {
    private final AvailableTimeRepository availableTimeRepository;
    private final TimeFrameRepository timeFrameRepository;
    private final AuthUtils authUtils;

    @Override
    public Page<AvailableTimeResponse> getAllByVacationUnitId(Long timeFrameId, Pageable pageable) {
        var availableTimePage = availableTimeRepository.findAllByVacationUnitIdAndDeletedIsFalse(timeFrameId, pageable);
        Page<AvailableTimeResponse> availableTimePageResponse = availableTimePage.map(AvailableTimeMapper.INSTANCE::toDtoResponse);
        return availableTimePageResponse;
    }

    @Override
    public Page<AvailableTimeResponse> getAllByPropertyId(Long propertyId, Pageable pageable) {
        var availableTimePage = availableTimeRepository.
                findAllByPropertyIdAndDeletedFalse(propertyId, pageable);
        Page<AvailableTimeResponse> availableTimePageResponse = availableTimePage.map(AvailableTimeMapper.INSTANCE::toDtoResponse);
        return availableTimePageResponse;
    }

    @Override
    public Page<AvailableTimeResponse> getAllByResortId(Long resortId, Pageable pageable) {
        var availableTimePage = availableTimeRepository.
                findAllByResortIdAndDeletedFalse(resortId, pageable);
        Page<AvailableTimeResponse> availableTimePageResponse = availableTimePage.map(AvailableTimeMapper.INSTANCE::toDtoResponse);
        return availableTimePageResponse;
    }

    @Override
    public AvailableTimeResponse get(Long id) {
        var availableTimeFound = availableTimeRepository.findByIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(AVAILABLE_TIME_NOT_FOUND));
        var availableTimeResponse = AvailableTimeMapper.INSTANCE.toDtoResponse(availableTimeFound);
        return availableTimeResponse;
    }

    @Override
    public AvailableTimeResponse create(Long timeFrameId, AvailableTimeRequest dtoRequest) {
        if (dtoRequest.getStartTime().after(dtoRequest.getEndTime()))
            throw new DataIntegrityViolationException("Start time must be before end time");
        var timeFrame = timeFrameRepository.
                findByIdAndIsDeletedIsFalse(timeFrameId).orElseThrow(() -> new EntityNotFoundException(TIME_FRAME_NOT_FOUND));
        authUtils.isBelongToMember(timeFrame.getUserId());
        if (timeFrame.getStatus() == TimeFrameStatus.PENDING)
            throw new DataIntegrityViolationException("TIME-FRAME is not accepted. Please contact to staff.");
        //check is in vacation unit time
        var checkIsInVacationUnitTime = timeFrameRepository.
                countMatchingTimeFrames(
                        timeFrameId,
                        dtoRequest.getStartTime(),
                        dtoRequest.getEndTime(),
                        TimeFrameStatus.ACCEPTED.toString()
                );
        if (checkIsInVacationUnitTime == 0L)
            throw new DataIntegrityViolationException("Your select time is not in range TIME-FRAME");
        var checkDuplicateWhichAny = availableTimeRepository.findOverlapsWhichAnyTimeDeposit(
                timeFrameId,
                dtoRequest.getStartTime(),
                dtoRequest.getEndTime(),
                AvailableTimeStatus.OPEN
        );
        if (checkDuplicateWhichAny.isPresent())
            throw new DataIntegrityViolationException("Duplicate with another time. AVAILABLE-TIME");
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
        var availableTime = availableTimeRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(AVAILABLE_TIME_NOT_FOUND));
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
