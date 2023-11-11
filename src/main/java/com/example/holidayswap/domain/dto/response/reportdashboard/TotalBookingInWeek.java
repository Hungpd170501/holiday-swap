package com.example.holidayswap.domain.dto.response.reportdashboard;

import lombok.Data;
import org.joda.time.LocalDate;

import java.util.Date;

@Data
public class TotalBookingInWeek {
    private Long totalBookingInMonday;
    private Long totalBookingInTuesday;
    private Long totalBookingInWednesday;
    private Long totalBookingInThursday;
    private Long totalBookingInFriday;
    private Long totalBookingInSaturday;
    private Long totalBookingInSunday;
    private Date mondayDate;
}
