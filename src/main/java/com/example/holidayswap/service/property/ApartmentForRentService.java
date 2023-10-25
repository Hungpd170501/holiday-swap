package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Set;

public interface ApartmentForRentService {
    Page<ApartmentForRentResponse> gets(String locationName, Long resortId, Date checkIn, Date checkOut, Long min, Long max,
                                        int guest, int numberBedsRoom, int numberBathRoom, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView,
                                        Set<Long> listOfPropertyType, Pageable pageable);

    ApartmentForRentResponse get(Long availableId);
}
