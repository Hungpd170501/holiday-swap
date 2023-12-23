package com.example.holidayswap.service.property.timeFame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.AvailableTimeRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableTimeService {
    Page<AvailableTimeResponse> getAllByVacationUnitId(Long vacationId, Pageable pageable);

    //    Page<TimeOffDepositResponse> getAllBy(Long propertyId, Pageable pageable);
    Page<AvailableTimeResponse> getAllByPropertyId(Long propertyId, Pageable pageable);

    List<AvailableTimeResponse> getAllByCoOwnerIdAndYear(Long timeFrameId, int year);

    Page<AvailableTimeResponse> getAllByResortId(Long resortId, Pageable pageable);

    List<AvailableTimeResponse> getAllByCoOwnerId(Long coOwnerId);

    AvailableTimeResponse get(Long id);

    void create(Long vacationId, AvailableTimeRequest timeOffDepositRequest);

    void update(Long id, AvailableTimeRequest timeOffDepositRequest);

    void update(Long id, AvailableTimeStatus availableTimeStatus);

    void delete(Long id);
}
