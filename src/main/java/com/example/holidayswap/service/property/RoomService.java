package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.RoomDTO;
import com.example.holidayswap.domain.dto.response.property.RoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Set;

public interface RoomService {
    Page<RoomResponse> gets(Date checkIn, Date checkOut, double min, double max, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView, Set<Long> listOfPropertyType, Pageable pageable);

    RoomDTO get(String roomId);
}
