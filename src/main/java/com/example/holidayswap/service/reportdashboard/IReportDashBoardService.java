package com.example.holidayswap.service.reportdashboard;

import java.util.Date;

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
}
