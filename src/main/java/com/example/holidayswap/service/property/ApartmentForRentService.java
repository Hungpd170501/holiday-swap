package com.example.holidayswap.service.property;

import java.util.Date;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;
import com.example.holidayswap.domain.dto.response.property.ResortApartmentForRentResponse;

public interface ApartmentForRentService {
    Page<ApartmentForRentResponse> gets(String locationName, Long resortId, Date checkIn, Date checkOut, Long min, Long max,
                                        int guest, int numberBedsRoom, int numberBathRoom, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView,
                                        Set<Long> listOfPropertyType, Pageable pageable);

    Page<ResortApartmentForRentResponse> getsResort(String locationName, Date checkIn, Date checkOut, Long min,
                                                    Long max,
                                                    int guest, int numberBedsRoom, int numberBathRoom, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView,
                                                    Set<Long> listOfPropertyType, Pageable pageable);

    ApartmentForRentResponse get(Long availableId);
}
