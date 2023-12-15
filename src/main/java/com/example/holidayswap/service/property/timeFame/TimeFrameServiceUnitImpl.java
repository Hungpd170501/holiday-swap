package com.example.holidayswap.service.property.timeFame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.TimeFrameRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.TimeFrameResponse;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.ContractType;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrame;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.timeFrame.AvailableTimeMapper;
import com.example.holidayswap.domain.mapper.property.timeFrame.TimeFrameMapper;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.repository.property.timeFrame.TimeFrameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.holidayswap.constants.ErrorMessage.CO_OWNER_NOT_FOUND;
import static com.example.holidayswap.constants.ErrorMessage.TIME_FRAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TimeFrameServiceUnitImpl implements TimeFrameService {
    private final TimeFrameRepository timeFrameRepository;
    private final CoOwnerRepository coOwnerRepository;
    private final AvailableTimeRepository availableTimeRepository;
    private final AvailableTimeMapper availableTimeMapper;
    private final BookingRepository bookingRepository;

    @Override
    public Page<TimeFrameResponse> getAllByCoOwner(Long propertyId, Long userId, String roomId, Pageable pageable) {
        var dtoResponses = timeFrameRepository.findAllByPropertyIdAAndUserIdAndRoomId(propertyId, userId, roomId, pageable).map((e) -> {
            var dto = TimeFrameMapper.INSTANCE.toDtoResponse(e);
            return dto;
        });
        return dtoResponses;
    }

    @Override
    public TimeFrameResponse get(Long id) {
        var timeFameFound =
                timeFrameRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(
                        "Time Frame not found!."));
        var timeFameResponse = TimeFrameMapper.INSTANCE.toDtoResponse(timeFameFound);
        timeFameResponse.setAvailableTimes(availableTimeRepository.findAllByTimeFrameIdAndIsDeletedIsFalse(timeFameFound.getId()).stream().map(availableTimeMapper::toDtoResponse).collect(Collectors.toList()));
        timeFameResponse.getAvailableTimes().forEach((e) -> {
            e.setTimeHasBooked(bookingRepository.findAllByTimeFrameIdAndStatus(timeFameFound.getId(), EnumBookingStatus.BookingStatus.SUCCESS));
        });
        return timeFameResponse;
    }

    @Override
    @Transactional
    public TimeFrameResponse create(CoOwnerId ownershipId, TimeFrameRequest dtoRequest) {
        var entity = TimeFrameMapper.INSTANCE.toEntity(dtoRequest);
        //Check begin time and end time is format
        //find ownership hold
        var coOwner = coOwnerRepository.findByPropertyIdAndUserIdAndIdRoomId(ownershipId.getPropertyId(), ownershipId.getUserId(), ownershipId.getRoomId()).orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND));
        //check user only have 1 timeframe
        var isTimeFrameCreated = timeFrameRepository.findOverlapWithStatusIsNotReject(ownershipId.getPropertyId(), ownershipId.getUserId(), ownershipId.getRoomId(), dtoRequest.getWeekNumber());
//        if (isTimeFrameCreated.isPresent() && isTimeFrameCreated.get().getStatus() != TimeFrameStatus.REJECTED)
//            throw new DuplicateRecordException("User only creates 1 timeframe for 1 property");
        //Declare list
        List<TimeFrame> checkTimeFrame;
        //Check overlaps with any, only have 1 timeframe in 1 time
        checkTimeFrame = timeFrameRepository.findOverlapWith(ownershipId.getPropertyId(), ownershipId.getRoomId(), dtoRequest.getWeekNumber(), ownershipId.getUserId());
        if (!checkTimeFrame.isEmpty()) {
            //DEEDED type: overlaps
            checkTimeFrame.forEach((e) -> {
                if (e.getStatus() != TimeFrameStatus.REJECTED && e.getStatus() == TimeFrameStatus.ACCEPTED) {
                    var checkCoOwner = coOwnerRepository.findByPropertyIdAndUserIdAndIdRoomId(e.getPropertyId(), e.getUserId(), e.getRoomId());

                    if (checkCoOwner.get().getType() == ContractType.DEEDED) {
                        throw new DuplicateRecordException("CO-OWNER--DEEDED overlaps with other. Overlaps on CO-OWNER");
                    }
                    //RIGHT-TO-USE: overlaps
                    else {
                        var coOwnersCheck = coOwnerRepository.checkOverlapsTimeOwnership(
                                //id
                                ownershipId.getPropertyId(), ownershipId.getUserId(), ownershipId.getRoomId(),
                                //time of co-owner instance
                                coOwner.getStartTime(), coOwner.getEndTime());
                        if (!coOwnersCheck.isEmpty()) {
                            throw new DuplicateRecordException("CO-OWNER--RIGHT-TO-USE overlaps with other. Overlaps on CO-OWNER-TIME");
                        }
                    }
                }
            });
        }
        Optional<TimeFrame> tf = timeFrameRepository.findByCoOwnerId(ownershipId.getRoomId(), ownershipId.getUserId(), ownershipId.getPropertyId(), dtoRequest.getWeekNumber());
        TimeFrame created = new TimeFrame();
        if (tf.isEmpty()) {
            //pass all then create
            entity.setPropertyId(ownershipId.getPropertyId());
            entity.setUserId(ownershipId.getUserId());
            entity.setRoomId(ownershipId.getRoomId());
            entity.setStatus(TimeFrameStatus.PENDING);
            created = timeFrameRepository.save(entity);
        } else {
            TimeFrame tf2 = tf.get();
            tf2.setStatus(TimeFrameStatus.PENDING);
            created = timeFrameRepository.save(tf2);
        }
        return TimeFrameMapper.INSTANCE.toDtoResponse(created);
    }

    @Override
    public TimeFrameResponse update(Long id, TimeFrameStatus timeFrameStatus) {
        var timeFrame = timeFrameRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(TIME_FRAME_NOT_FOUND));
        CoOwner coOwner = timeFrame.getCoOwner();
        CoOwnerId ownershipId = timeFrame.getCoOwner().getId();
        if (timeFrameStatus == TimeFrameStatus.ACCEPTED) {
            //check
            List<TimeFrame> checkTimeFrame;
            checkTimeFrame = timeFrameRepository.findOverlapWith(ownershipId.getPropertyId(), ownershipId.getRoomId(), timeFrame.getWeekNumber(), ownershipId.getUserId());
            if (!checkTimeFrame.isEmpty()) {
                //DEEDED type: overlaps
                checkTimeFrame.forEach((e) -> {
                    if (e.getStatus() != TimeFrameStatus.REJECTED && e.getStatus() == TimeFrameStatus.ACCEPTED) {
                        var checkCoOwner = coOwnerRepository.findByPropertyIdAndUserIdAndIdRoomId(e.getPropertyId(), e.getUserId(), e.getRoomId());

                        if (checkCoOwner.get().getType() == ContractType.DEEDED) {
                            throw new DuplicateRecordException("CO-OWNER--DEEDED overlaps with other. Overlaps on CO-OWNER");
                        }
                        //RIGHT-TO-USE: overlaps
                        else {
                            var coOwnersCheck = coOwnerRepository.checkOverlapsTimeOwnership(
                                    //id
                                    ownershipId.getPropertyId(), ownershipId.getUserId(), ownershipId.getRoomId(),
                                    //time of co-owner instance
                                    coOwner.getStartTime(), coOwner.getEndTime());
                            if (!coOwnersCheck.isEmpty()) {
                                throw new DuplicateRecordException("CO-OWNER--RIGHT-TO-USE overlaps with other. Overlaps on CO-OWNER-TIME");
                            }
                        }
                    }
                });
            }
        }
        timeFrame.setStatus(timeFrameStatus);
        timeFrameRepository.save(timeFrame);
        return TimeFrameMapper.INSTANCE.toDtoResponse(timeFrame);
    }

    @Override
    public void delete(Long id) {
        var timeFrame = timeFrameRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(TIME_FRAME_NOT_FOUND));
        timeFrame.setDeleted(true);
        timeFrameRepository.save(timeFrame);
    }
}
