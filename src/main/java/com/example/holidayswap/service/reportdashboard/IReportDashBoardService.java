package com.example.holidayswap.service.reportdashboard;

import com.example.holidayswap.domain.dto.response.reportdashboard.TotalBookingInWeek;
import com.example.holidayswap.domain.dto.response.reportdashboard.TotalBookingInYear;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

public interface IReportDashBoardService {
    Double pointDepositToDay();
    Double pointDepositWeek();
    Double pointDepositDate(Date date);
    Double pointDepositMonth();
    Double pointDepositeInMonthAndYear(Date date);

    Double commissionOfBookingToDay();
    Double commissionOfBookingWeek();
    Double commissionOfBookingDate(Date date);
    Double commissionOfBookingMonth();
    Double commissionOfBookingInMonthAndYear(Date date);
    TotalBookingInWeek totalBookingInWeek(Date monday,String type);
    TotalBookingInYear totalBookingInYear(Integer year,String type);

    TotalBookingInWeek totalComissionInWeek(Date monday,String type);
    TotalBookingInYear totalComissionInYear(Integer monday,String type);

    TotalBookingInWeek totalPointInWeek(Date monday,String type);
    TotalBookingInYear totalPointInYear(Integer year,String type);
}
