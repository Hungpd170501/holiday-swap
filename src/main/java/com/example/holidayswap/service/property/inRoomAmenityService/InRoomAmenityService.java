package com.example.holidayswap.service.property.inRoomAmenityService;

import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.InRoomAmenityRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface InRoomAmenityService {
    Page<InRoomAmenityResponse> gets(String name, Pageable pageable);

    InRoomAmenityResponse get(Long id);

    InRoomAmenityResponse create(InRoomAmenityRequest inRoomAmenityRequest);

    InRoomAmenityResponse update(Long id, InRoomAmenityRequest inRoomAmenityRequest);

    void delete(Long id);

}
