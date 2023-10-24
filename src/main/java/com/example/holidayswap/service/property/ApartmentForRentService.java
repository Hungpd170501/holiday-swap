package com.example.holidayswap.service.property;

import java.util.Date;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;

public interface ApartmentForRentService {
    Page<ApartmentForRentResponse> gets(Date checkIn, Date checkOut, double min, double max, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView, Set<Long> listOfPropertyType, Pageable pageable);

    ApartmentForRentResponse get(Long availableId);
}
