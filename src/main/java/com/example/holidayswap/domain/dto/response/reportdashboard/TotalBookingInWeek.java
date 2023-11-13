package com.example.holidayswap.domain.dto.response.reportdashboard;

import lombok.Data;
import org.joda.time.LocalDate;

import java.util.Date;

@Data
public class TotalBookingInWeek {
    private Double totalBookingInMonday;
    private Double totalBookingInTuesday;
    private Double totalBookingInWednesday;
    private Double totalBookingInThursday;
    private Double totalBookingInFriday;
    private Double totalBookingInSaturday;
    private Double totalBookingInSunday;
    private Date mondayDate;
}
