package com.example.holidayswap.service.property.timeFame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.TimeFrameRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.TimeFrameResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.coOwner.ContractType;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrame;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.timeFrame.TimeFrameMapper;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.repository.property.timeFrame.TimeFrameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.holidayswap.constants.ErrorMessage.CO_OWNER_NOT_FOUND;
import static com.example.holidayswap.constants.ErrorMessage.TIME_FRAME_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeFrameServiceImpl implements TimeFrameService {
    private final TimeFrameRepository timeFrameRepository;
    private final CoOwnerRepository coOwnerRepository;
    private final AvailableTimeRepository availableTimeRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Page<TimeFrameResponse> getAllByCoOwner(Long coOwnerId, Pageable pageable) {
        var dtoResponses = timeFrameRepository.findAllByCoOwnerId(coOwnerId, pageable);
        var rs = dtoResponses.map(TimeFrameMapper.INSTANCE::toDtoResponse);
        return rs;
    }

    @Override
    public TimeFrameResponse get(Long id) {
        var tf = timeFrameRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TIME_FRAME_NOT_FOUND));
        var rs = TimeFrameMapper.INSTANCE.toDtoResponse(tf);
        return rs;
    }

    @Override
    public void create(Long coOwnerId, TimeFrameRequest dtoRequest) {
        var e = TimeFrameMapper.INSTANCE.toEntity(dtoRequest);
        e.setCoOwnerId(coOwnerId);
        timeFrameRepository.save(e);
    }

    //check the time frame is duplicate with other user with status is verify
    void checkIsCoOwnerDuplicate(CoOwner co, ContractType contractType, Integer weekNumber) {
        if (contractType == ContractType.DEEDED) {
            var listTimeFrameDEEDEDDuplicate = timeFrameRepository.findByPropertyIdAndRoomIdAndWeekNumberDEEDEDDuplicate(co.getPropertyId(),
//                    co.getUserId(),
                    co.getRoomId(), co.getStartTime(), weekNumber);
            if (!listTimeFrameDEEDEDDuplicate.isEmpty())
                throw new DataIntegrityViolationException("Duplicate!.");
            var listTimeFrameDEEDEDDuplicate2 = timeFrameRepository.findByPropertyIdAndRoomIdAndWeekNumberDEEDEDDuplicate(co.getPropertyId(),
//                    co.getUserId(),
                    co.getRoomId(),
                    weekNumber);
            if (!listTimeFrameDEEDEDDuplicate2.isEmpty())
                throw new DataIntegrityViolationException("Duplicate!.");
        } else {
            var listTimeFrameRIGHTTOUSEDuplicate = timeFrameRepository.findByPropertyIdAndRoomIdAndWeekNumberRIGHTTOUSEDuplicate(co.getPropertyId(),
//                    co.getUserId(),
                    co.getRoomId(), co.getStartTime(), co.getEndTime(), weekNumber);
            if (!listTimeFrameRIGHTTOUSEDuplicate.isEmpty())
                throw new DataIntegrityViolationException("Duplicate!.");
        }
    }

    void checkUserIsCreateThisWeek(CoOwner co, ContractType contractType, Integer weekNumber) {
        var tf = timeFrameRepository.findByPropertyIdAndUserIdAndRoomIdAndCoOwnerTypeAndWeekNumber(co.getPropertyId(),
                co.getUserId(), co.getRoomId(), co.getType().toString(), weekNumber, co.getStartTime(), co.getEndTime());
        if (tf.isPresent()) throw new DataIntegrityViolationException("User already created this week!.");
    }

    @Override
    @Transactional
    public void create(Long coOwnerId, Integer weekNumber) {
        var co = coOwnerRepository.findById(coOwnerId).orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND));
        checkIsCoOwnerDuplicate(co, co.getType(), weekNumber);
        checkUserIsCreateThisWeek(co, co.getType(), weekNumber);
        //Deeded
        if (co.getType() == ContractType.DEEDED) {
            //start year
        }
        //Right to use
        else if (co.getType() == ContractType.RIGHT_TO_USE) {

        }
        var e = new TimeFrame(null, weekNumber, coOwnerId, null);
        timeFrameRepository.save(e);
    }

    @Override
    public void update(Long id, CoOwnerStatus coOwnerStatus) {
        var tf = timeFrameRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TIME_FRAME_NOT_FOUND));
        var co = coOwnerRepository.findById(tf.getCoOwnerId()).orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND));
//        var coIsCreated = coOwnerRepository.find
        if (coOwnerStatus == CoOwnerStatus.ACCEPTED) {
            checkIsCoOwnerDuplicate(co, co.getType(), tf.getWeekNumber());
        }
        //Deeded
        if (co.getType() == ContractType.DEEDED) {
            //start year
        }
        //Right to use
        else if (co.getType() == ContractType.RIGHT_TO_USE) {

        }
    }

    @Override
    public void delete(Long id) {
        var tf = timeFrameRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TIME_FRAME_NOT_FOUND));

    }

    @Override
    public void appendToCo(Long coOwnerId, TimeFrame tf) {
        var tfFound = timeFrameRepository.findById(tf.getId()).orElseThrow(() -> new EntityNotFoundException(TIME_FRAME_NOT_FOUND));
        tfFound.setCoOwnerId(coOwnerId);
        timeFrameRepository.save(tfFound);
    }
}
