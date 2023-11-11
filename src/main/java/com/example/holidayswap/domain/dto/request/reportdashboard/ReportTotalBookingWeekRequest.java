package com.example.holidayswap.domain.dto.request.reportdashboard;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Nullable;
import java.util.Date;

@Data
public class ReportTotalBookingWeekRequest {
    @Nullable
    private Date monday;
    private String type;
}
