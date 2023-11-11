package com.example.holidayswap.service.reportdashboard;

import com.example.holidayswap.domain.dto.response.reportdashboard.TotalBookingInWeek;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.payment.TransactionRepository;
import com.example.holidayswap.utils.Helper;
import lombok.AllArgsConstructor;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.util.Date;
import org.joda.time.DateTimeConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public TotalBookingInWeek totalBookingInWeek(Date mondayDate,String type) {
        LocalDate now = new LocalDate();
        LocalDate sunday;
        LocalDate monday = new LocalDate(mondayDate);
        String day;
        Long count;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
        LocalDate localDateParseFromString;
        if(!type.equals("back") && !type.equals("next") && !type.equals("current")){
            throw new RuntimeException("Type must be 'back' or 'next'");
        }

        if(type.equals("current") || mondayDate== null){
           monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
           sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);
        }else if(type.equals("back")){
            monday = monday.minusWeeks(1);
            sunday = monday.plusDays(6);
        }else {
            monday = monday.plusWeeks(1);
            sunday = monday.plusDays(6);
        }
        String mondayWithOutTime = Helper.convertDateToString(monday.toDate());
        String sundayWithOutTime = Helper.convertDateToString(sunday.toDate());
        var listBookingWeek= bookingRepository.findAllByDateBookingBetween(mondayWithOutTime, sundayWithOutTime);
        Map<String, Long> totalBookingsByDay = new HashMap<>();
        for (var item: listBookingWeek) {
            localDateParseFromString = LocalDate.parse(item.getDateBooking(), formatter);
            day =localDateParseFromString.dayOfWeek().getAsText();
            if (totalBookingsByDay.get(day) == null) {
                totalBookingsByDay.put(day, 1L);
            }else {
                count =totalBookingsByDay.get(day);
                totalBookingsByDay.put(day, ++count);
            }
        }
        TotalBookingInWeek totalBookingInWeek = new TotalBookingInWeek();
        totalBookingInWeek.setTotalBookingInMonday(totalBookingsByDay.get("Monday") == null ? 0 : totalBookingsByDay.get("Monday"));
        totalBookingInWeek.setTotalBookingInTuesday(totalBookingsByDay.get("Tuesday") == null ? 0 : totalBookingsByDay.get("Tuesday"));
        totalBookingInWeek.setTotalBookingInWednesday(totalBookingsByDay.get("Wednesday") == null ? 0 : totalBookingsByDay.get("Wednesday"));
        totalBookingInWeek.setTotalBookingInThursday(totalBookingsByDay.get("Thursday") == null ? 0 : totalBookingsByDay.get("Thursday"));
        totalBookingInWeek.setTotalBookingInFriday(totalBookingsByDay.get("Friday") == null ? 0 : totalBookingsByDay.get("Friday"));
        totalBookingInWeek.setTotalBookingInSaturday(totalBookingsByDay.get("Saturday") == null ? 0 : totalBookingsByDay.get("Saturday"));
        totalBookingInWeek.setTotalBookingInSunday(totalBookingsByDay.get("Sunday") == null ? 0 : totalBookingsByDay.get("Sunday"));
        totalBookingInWeek.setMondayDate(monday.toDate());

        return totalBookingInWeek;
    }
}
