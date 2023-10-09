package com.example.holidayswap.service.property.timeFame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.TimeFrameRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.TimeFrameResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeFrameService {
    Page<TimeFrameResponse> getAllByPropertyId(Long propertyId, Pageable pageable);

    Page<TimeFrameResponse> getAllByResortId(Long resortId, Pageable pageable);

    TimeFrameResponse get(Long id);

    TimeFrameResponse create(CoOwnerId ownershipId, TimeFrameRequest dtoRequest);

    TimeFrameResponse update(Long id, TimeFrameRequest dtoRequest);

    TimeFrameResponse update(Long id, TimeFrameStatus timeFrameStatus);

    void delete(Long id);
}
