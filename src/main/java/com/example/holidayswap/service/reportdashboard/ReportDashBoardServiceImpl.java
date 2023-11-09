package com.example.holidayswap.service.reportdashboard;

import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.payment.TransactionRepository;
import com.example.holidayswap.utils.Helper;
import lombok.AllArgsConstructor;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
@AllArgsConstructor
public class ReportDashBoardServiceImpl implements IReportDashBoardService{

    private final TransactionRepository transactionRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Double pointDepositToDay() {
        var today = Helper.getCurrentDateWithoutTime();
        var listDepositToday= transactionRepository.findAllByPaymentDateContainingAndStatus(today, EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
        var totalDepositToday = 0.0;
        for (var item: listDepositToday) {
            totalDepositToday += item.getAmountCoinDeposit();
        }
        return totalDepositToday;
    }

    @Override
    public Double pointDepositWeek() {

        LocalDate now = new LocalDate();
        LocalDate monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
        LocalDate sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);
        var listDepositWeek= transactionRepository.findAllByPaymentDateBetween(monday.toString(), sunday.toString());
        var totalDepositWeek = 0.0;
        for (var item: listDepositWeek) {
            totalDepositWeek += item.getAmountCoinDeposit();
        }
        return totalDepositWeek;
    }

    @Override
    public Double pointDepositDate(Date date) {

        var listDepositDate= transactionRepository.findAllByPaymentDateContainingAndStatus(Helper.convertDateToString(date), EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
        var totalDepositDate = 0.0;
        for (var item: listDepositDate) {
            totalDepositDate += item.getAmountCoinDeposit();
        }
        return totalDepositDate;
    }

    @Override
    public Double pointDepositMonth() {
        var listDepositMonth= transactionRepository.findAllByPaymentDateContainingAndStatus(Helper.getMonthAndYearCurrent(), EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
        var totalDepositMonth = 0.0;
        for (var item: listDepositMonth) {
            totalDepositMonth += item.getAmountCoinDeposit();
        }
        return totalDepositMonth;
    }

    @Override
    public Double pointDepositeInMonthAndYear(Date date) {
        var listDepositMonth= transactionRepository.findAllByPaymentDateContainingAndStatus(Helper.convertDateAndSplitDayToString(date), EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
        var totalDepositMonth = 0.0;
        for (var item: listDepositMonth) {
            totalDepositMonth += item.getAmountCoinDeposit();
        }
        return totalDepositMonth;
    }

    @Override
    public Double commissionOfBookingToDay() {
        var today = Helper.getCurrentDateWithoutTime();
        var listBookingToday= bookingRepository.findAllByDateBookingContaining(today);
        var totalCommissionToday = 0.0;
        for (var item: listBookingToday) {
            totalCommissionToday += item.getCommission();
        }
        return totalCommissionToday;
    }

    @Override
    public Double commissionOfBookingWeek() {
        LocalDate now = new LocalDate();
        LocalDate monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
        LocalDate sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);
        var listBookingWeek= bookingRepository.findAllByDateBookingBetween(monday.toString(), sunday.toString());
        var totalCommissionWeek = 0.0;
        for (var item: listBookingWeek) {
            totalCommissionWeek += item.getCommission();
        }
        return totalCommissionWeek;
    }

    @Override
    public Double commissionOfBookingDate(Date date) {
        var listBookingDate= bookingRepository.findAllByDateBookingContaining(Helper.convertDateToString(date));
        var totalCommissionDate = 0.0;
        for (var item: listBookingDate) {
            totalCommissionDate += item.getCommission();
        }
        return totalCommissionDate;
    }

    @Override
    public Double commissionOfBookingMonth() {
        var listBookingMonth= bookingRepository.findAllByDateBookingContaining(Helper.getMonthAndYearCurrent());
        var totalCommissionMonth = 0.0;
        for (var item: listBookingMonth) {
            totalCommissionMonth += item.getCommission();
        }
        return totalCommissionMonth;
    }

    @Override
    public Double commissionOfBookingInMonthAndYear(Date date) {

        var listBookingMonth= bookingRepository.findAllByDateBookingContaining(Helper.convertDateAndSplitDayToString(date));
        var totalCommissionMonth = 0.0;
        for (var item: listBookingMonth) {
            totalCommissionMonth += item.getCommission();
        }
        return totalCommissionMonth;
    }
}
