package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Set;

public interface ApartmentForRentService {
    Page<ApartmentForRentResponse> gets(Date checkIn, Date checkOut, double min, double max, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView, Set<Long> listOfPropertyType, Pageable pageable);

    ApartmentForRentResponse get(CoOwnerId coOwnerId);
}
