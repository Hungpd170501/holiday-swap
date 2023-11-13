package com.example.holidayswap.domain.dto.response.reportdashboard;

import lombok.Data;

@Data
public class TotalBookingInYear {
    private Double totalBookingInJanuary;
    private Double totalBookingInFebruary;
    private Double totalBookingInMarch;
    private Double totalBookingInApril;
    private Double totalBookingInMay;
    private Double totalBookingInJune;
    private Double totalBookingInJuly;
    private Double totalBookingInAugust;
    private Double totalBookingInSeptember;
    private Double totalBookingInOctober;
    private Double totalBookingInNovember;
    private Double totalBookingInDecember;
    private Integer year;
}
