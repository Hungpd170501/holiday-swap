package com.example.holidayswap.service.property.timeFame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.TimeFrameRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.TimeFrameResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeFrameService {
    Page<TimeFrameResponse> getAllByCoOwner(Long coOwnerId, Pageable pageable);

    TimeFrameResponse get(Long id);

    void create(Long coOwnerId, TimeFrameRequest dtoRequest);

    void create(Long coOwnerId, Integer weekNumber);

    void update(Long id, CoOwnerStatus coOwnerStatus);

    void delete(Long id);

    void appendToCo(Long coOwnerId, TimeFrame tf);
}
