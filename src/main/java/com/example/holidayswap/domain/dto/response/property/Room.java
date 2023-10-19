package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Room {
    private String roomId;
    private double min;
    private double max;
    private Date startDate;
    private Date endDate;
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

    public Room(String roomId, double min, double max) {
        this.roomId = roomId;
        this.min = min;
        this.max = max;
    }

    public Room(String roomId, double min, double max, Date startDate, Date endDate) {
        this.roomId = roomId;
        this.min = min;
        this.max = max;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
