package com.example.holidayswap.service.property.timeFame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.AvailableTimeRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrame;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.timeFrame.AvailableTimeMapper;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.repository.resort.ResortRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.holidayswap.constants.ErrorMessage.AVAILABLE_TIME_NOT_FOUND;
import static com.example.holidayswap.constants.ErrorMessage.CO_OWNER_NOT_FOUND;
import static java.time.temporal.ChronoUnit.WEEKS;

@Service
@Slf4j
@RequiredArgsConstructor
public class AvailableTimeServiceImpl implements AvailableTimeService {
    private final AvailableTimeRepository availableTimeRepository;
    private final CoOwnerRepository coOwnerRepository;
    private final BookingRepository bookingRepository;
    private final ResortRepository resortRepository;
    private final PropertyRepository propertyRepository;

    List<Integer> numberOfWeeks(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(LocalDate.now()))
            throw new DataIntegrityViolationException("Date must greater than now!.");
        endDate = endDate.minusDays(1);
        int addWeek = 0;
//        if (startDate.get(WeekFields.ISO.weekOfYear()) < endDate.get(WeekFields.ISO.weekOfYear())) {
//            addWeek = 1;
//        }
        long weeks = WEEKS.between(startDate, endDate) + addWeek;//Get the number of weeks in your case (52)
        List<Integer> numberWeeks = new ArrayList<>();
        if (weeks >= 0) {
            int week = 0;
            do {
                //Get the number of week
                LocalDate dt = startDate.plusWeeks(week);
                int weekNumber = dt.get(WeekFields.ISO.weekOfYear());
//                numberWeeks.add(String.format("%d-W%d", dt.getYear(), weekNumber));
                if (weekNumber > 52) weekNumber = 1;
                numberWeeks.add(weekNumber);
                week++;
            } while (week <= weeks);
        }
        return numberWeeks;
    }

    void isOverlaps(LocalDate startTime, LocalDate endTime, Long coOwnerId) {
        var overlaps = availableTimeRepository.isOverlaps(startTime, endTime, coOwnerId);
        if (!overlaps.isEmpty()) throw new DataIntegrityViolationException("Overlaps with other public time!.");
    }

    void isValidDate(LocalDate startTime, LocalDate endTime) {
        if (startTime.isEqual(endTime)) throw new DataIntegrityViolationException("Start time can not equal end Time");
    }

    @Override
    public Page<AvailableTimeResponse> getAllByVacationUnitId(Long vacationId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<AvailableTimeResponse> getAllByPropertyId(Long propertyId, Pageable pageable) {
        return null;
    }

    @Override
    public List<AvailableTimeResponse> getAllByCoOwnerIdAndYear(Long coOwnerId, int year) {
        var list = availableTimeRepository.findByCoOwnerIdAndYear(coOwnerId, year);
        return list.stream().map(AvailableTimeMapper.INSTANCE::toDtoResponse).collect(Collectors.toList());
    }

    @Override
    public List<AvailableTimeResponse> getAllByCoOwnerId(Long coOwnerId) {
        var list = availableTimeRepository.findByCoOwnerId(coOwnerId);
        return list.stream().map(AvailableTimeMapper.INSTANCE::toDtoResponse).collect(Collectors.toList());
    }

    @Override
    public Page<AvailableTimeResponse> getAllByResortId(Long resortId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<AvailableTimeResponse> getAllByCoOwnerId(Long coOwnerId, Pageable pageable) {
        var availableTimeList = availableTimeRepository.findAllByCoOwnerIdAndIsDeletedIsFalse(coOwnerId, pageable);
        Page<AvailableTimeResponse> responses = availableTimeList.map(AvailableTimeMapper.INSTANCE::toDtoResponse);
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
    public void create(Long coOwnerId, AvailableTimeRequest rq) {
        isValidDate(rq.getStartTime(), rq.getEndTime());
        var co = coOwnerRepository.findById(coOwnerId).orElseThrow(() -> new EntityNotFoundException(CO_OWNER_NOT_FOUND));
        if (co.getStatus() != CoOwnerStatus.ACCEPTED) {
            throw new DataIntegrityViolationException("This Co-Owner is not accepted! Please contact for Staff!");
        }
        isInMaintain(rq.getStartTime(), rq.getEndTime(), co);
        checkIsInDeactivate(rq.getStartTime(), rq.getEndTime(), co);
        var property = propertyRepository.findById(co.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Apartment not found"));
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
        var listWeek = numberOfWeeks(rq.getStartTime(), rq.getEndTime());
        List<Integer> listWeekInCo = co.getTimeFrames().stream().map(TimeFrame::getWeekNumber).toList();
        for (Integer w : listWeek) {
            boolean flagIsBelongInCoO = listWeekInCo.contains(w);
            if (!flagIsBelongInCoO) throw new DataIntegrityViolationException("Date is not in range!.");
        }
        isOverlaps(rq.getStartTime(), rq.getEndTime(), coOwnerId);
        var at = AvailableTimeMapper.INSTANCE.toEntity(rq);
        at.setStatus(AvailableTimeStatus.OPEN);
        at.setCoOwnerId(coOwnerId);
        availableTimeRepository.save(at);
    }

    private void isInMaintain(LocalDate start, LocalDate end, CoOwner coOwner) {
        //parse time
        LocalDateTime startD = start.atStartOfDay();
        LocalDateTime endD = end.atStartOfDay();
        var resMaintain = coOwner.getProperty().getResort().getResortMaintainces().stream().filter(e -> e.getType() == ResortStatus.MAINTENANCE);
        var propMaintain = coOwner.getProperty().getPropertyMaintenance().stream().filter(e -> e.getType() == PropertyStatus.MAINTENANCE);
        resMaintain.forEach(e -> {
            if ((startD.isAfter(e.getStartDate()) || startD.isEqual(e.getStartDate())) && (startD.isBefore(e.getEndDate()) || startD.isEqual(e.getEndDate()))) {
                throw new DataIntegrityViolationException("Resort in maintain this time. Try to another time!.");
            }
            if ((endD.isAfter(e.getStartDate()) || endD.isEqual(e.getStartDate())) && (endD.isBefore(e.getEndDate()) || endD.isEqual(e.getEndDate()))) {
                throw new DataIntegrityViolationException("Resort in maintain this time. Try to another time!.");
            }
        });
        propMaintain.forEach(e -> {
            if ((startD.isAfter(e.getStartDate()) || startD.isEqual(e.getStartDate())) && (startD.isBefore(e.getEndDate()) || startD.isEqual(e.getEndDate()))) {
                throw new DataIntegrityViolationException("Resort in maintain this time. Try to another time!.");
            }
            if ((endD.isAfter(e.getStartDate()) || endD.isEqual(e.getStartDate())) && (endD.isBefore(e.getEndDate()) || endD.isEqual(e.getEndDate()))) {
                throw new DataIntegrityViolationException("Resort in maintain this time. Try to another time!.");
            }
        });
    }

    private void checkIsInDeactivate(LocalDate start, LocalDate end, CoOwner coOwner) {
        LocalDateTime startD = start.atStartOfDay();
        LocalDateTime endD = end.atStartOfDay();
        var resDeactivate = coOwner.getProperty().getResort().getResortMaintainces().stream().filter(e -> e.getType() == ResortStatus.DEACTIVATE).toList();
        var propDeactivate = coOwner.getProperty().getPropertyMaintenance().stream().filter(e -> e.getType() == PropertyStatus.DEACTIVATE).toList();
        if (!resDeactivate.isEmpty()) {
            if ((resDeactivate.get(0).getStartDate().isAfter(startD) || resDeactivate.get(0).getStartDate().isEqual(startD)) && (resDeactivate.get(0).getStartDate().isBefore(endD) || resDeactivate.get(0).getStartDate().isEqual(endD))) {
                throw new DataIntegrityViolationException("Resort is deactivate this time. Try to another time!.");
            }
        }
        if (!propDeactivate.isEmpty()) {
            if ((propDeactivate.get(0).getStartDate().isAfter(startD) || propDeactivate.get(0).getStartDate().isEqual(startD)) && (propDeactivate.get(0).getStartDate().isBefore(endD) || propDeactivate.get(0).getStartDate().isEqual(endD))) {
                throw new DataIntegrityViolationException("Property is deactivate this time. Try to another time!.");
            }
        }
    }

    private void checkIsInDeactivateTime(LocalDate start, LocalDate end, CoOwner coOwner) {
    }

    @Override
    public void update(Long id, AvailableTimeRequest timeOffDepositRequest) {

    }

    @Override
    public void update(Long id, AvailableTimeStatus availableTimeStatus) {

    }

    @Override
    public void delete(Long id) {
        var av = availableTimeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(AVAILABLE_TIME_NOT_FOUND));
        av.setDeleted(true);
        availableTimeRepository.save(av);
    }

}
