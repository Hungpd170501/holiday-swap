package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;

import java.util.List;

public class Room {
    private String roomId;
    private List<AvailableTime> availableTimes;

    public Room() {
    }

    public Room(String roomId, List<AvailableTime> availableTimes) {
        this.roomId = roomId;
        this.availableTimes = availableTimes;
    }

    public Room(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<AvailableTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<AvailableTime> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
