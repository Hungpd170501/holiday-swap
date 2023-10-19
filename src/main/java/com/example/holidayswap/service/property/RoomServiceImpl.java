package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.Room;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.repository.property.timeFrame.TimeFrameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final TimeFrameRepository timeFrameRepository;
    private final AvailableTimeRepository availableTimeRepository;

    @Override
    public Page<Room> gets(Date checkIn, Date checkOut, double min, double max, Pageable pageable) {
        var response = timeFrameRepository.findHavingAvailableTime(
                checkIn,
                checkOut,
                min,
                max,
                pageable);
        response.forEach(e -> {
            e.setAvailableTimes(availableTimeRepository.findAllByRoomId(e.getRoomId()));
        });
        return response;
    }

    @Override
    public Room get(String roomId) {
        var response = timeFrameRepository.findRoomByRoomId(
                roomId).orElseThrow(() -> new EntityNotFoundException("Room is not found or No time is public."));
        response.setAvailableTimes(availableTimeRepository.findAllByRoomId(roomId));
        return response;
    }
}
