package com.example.holidayswap.domain.dto.request.reportdashboard;

import lombok.Data;

import javax.annotation.Nullable;
import java.util.Date;

@Data
public class ReportTotalBookingYearRequest {
    @Nullable
    private Integer year;
    private String type;
}
