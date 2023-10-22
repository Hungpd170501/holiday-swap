package com.example.holidayswap.service.property.timeFame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.AvailableTimeRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
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

    Page<AvailableTimeResponse> getAllByResortId(Long resortId, Pageable pageable);

    List<AvailableTimeResponse> getAllByCoOwnerId(CoOwnerId coOwnerId);

    AvailableTimeResponse get(Long id);

    AvailableTimeResponse create(Long vacationId, AvailableTimeRequest timeOffDepositRequest);

    AvailableTimeResponse update(Long id, AvailableTimeRequest timeOffDepositRequest);

    AvailableTimeResponse update(Long id, AvailableTimeStatus availableTimeStatus);

    void delete(Long id);
}
