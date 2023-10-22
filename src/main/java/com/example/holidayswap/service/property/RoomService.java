package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.RoomDTO;
import com.example.holidayswap.domain.dto.response.property.RoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface RoomService {
    Page<RoomResponse> gets(Date checkIn, Date checkOut, double min, double max, Pageable pageable);

    RoomDTO get(String roomId);
}
