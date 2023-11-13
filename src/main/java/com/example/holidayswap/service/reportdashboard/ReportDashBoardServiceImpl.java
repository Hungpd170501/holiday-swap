package com.example.holidayswap.service.reportdashboard;

import com.example.holidayswap.domain.dto.response.reportdashboard.TotalBookingInWeek;
import com.example.holidayswap.domain.dto.response.reportdashboard.TotalBookingInYear;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.payment.TransactionRepository;
import com.example.holidayswap.utils.Helper;
import com.google.rpc.Help;
import lombok.AllArgsConstructor;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportDashBoardServiceImpl implements IReportDashBoardService {

    private final TransactionRepository transactionRepository;
    private final BookingRepository bookingRepository;


    @Override
    public Double pointDepositToDay() {
        var today = Helper.getCurrentDateWithoutTime();
        var listDepositToday = transactionRepository.findAllByPaymentDateContainingAndStatus(today, EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
        var totalDepositToday = 0.0;
        for (var item : listDepositToday) {
            totalDepositToday += item.getAmountCoinDeposit();
        }
        return totalDepositToday;
    }

    @Override
    public Double pointDepositWeek() {

        LocalDate now = new LocalDate();
        LocalDate monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
        LocalDate sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);
        var listDepositWeek = transactionRepository.findAllByPaymentDateBetween(monday.toString(), sunday.toString());
        var totalDepositWeek = 0.0;
        for (var item : listDepositWeek) {
            totalDepositWeek += item.getAmountCoinDeposit();
        }
        return totalDepositWeek;
    }

    @Override
    public Double pointDepositDate(Date date) {

        var listDepositDate = transactionRepository.findAllByPaymentDateContainingAndStatus(Helper.convertDateToString(date), EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
        var totalDepositDate = 0.0;
        for (var item : listDepositDate) {
            totalDepositDate += item.getAmountCoinDeposit();
        }
        return totalDepositDate;
    }

    @Override
    public Double pointDepositMonth() {
        var listDepositMonth = transactionRepository.findAllByPaymentDateContainingAndStatus(Helper.getMonthAndYearCurrent(), EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
        var totalDepositMonth = 0.0;
        for (var item : listDepositMonth) {
            totalDepositMonth += item.getAmountCoinDeposit();
        }
        return totalDepositMonth;
    }

    @Override
    public Double pointDepositeInMonthAndYear(Date date) {
        var listDepositMonth = transactionRepository.findAllByPaymentDateContainingAndStatus(Helper.convertDateAndSplitDayToString(date), EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
        var totalDepositMonth = 0.0;
        for (var item : listDepositMonth) {
            totalDepositMonth += item.getAmountCoinDeposit();
        }
        return totalDepositMonth;
    }

    @Override
    public Double commissionOfBookingToDay() {
        var today = Helper.getCurrentDateWithoutTime();
        var listBookingToday = bookingRepository.findAllByDateBookingContaining(today);
        var totalCommissionToday = 0.0;
        for (var item : listBookingToday) {
            totalCommissionToday += item.getCommission();
        }
        return totalCommissionToday;
    }

    @Override
    public Double commissionOfBookingWeek() {
        LocalDate now = new LocalDate();
        LocalDate monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
        LocalDate sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);
        var listBookingWeek = bookingRepository.findAllByDateBookingBetween(monday.toString(), sunday.toString());
        var totalCommissionWeek = 0.0;
        for (var item : listBookingWeek) {
            totalCommissionWeek += item.getCommission();
        }
        return totalCommissionWeek;
    }

    @Override
    public Double commissionOfBookingDate(Date date) {
        var listBookingDate = bookingRepository.findAllByDateBookingContaining(Helper.convertDateToString(date));
        var totalCommissionDate = 0.0;
        for (var item : listBookingDate) {
            totalCommissionDate += item.getCommission();
        }
        return totalCommissionDate;
    }

    @Override
    public Double commissionOfBookingMonth() {
        var listBookingMonth = bookingRepository.findAllByDateBookingContaining(Helper.getMonthAndYearCurrent());
        var totalCommissionMonth = 0.0;
        for (var item : listBookingMonth) {
            totalCommissionMonth += item.getCommission();
        }
        return totalCommissionMonth;
    }

    @Override
    public Double commissionOfBookingInMonthAndYear(Date date) {

        var listBookingMonth = bookingRepository.findAllByDateBookingContaining(Helper.convertDateAndSplitDayToString(date));
        var totalCommissionMonth = 0.0;
        for (var item : listBookingMonth) {
            totalCommissionMonth += item.getCommission();
        }
        return totalCommissionMonth;
    }

    @Override
    public TotalBookingInWeek totalBookingInWeek(Date mondayDate, String type) {
        LocalDate now = new LocalDate();
        LocalDate sunday;
        LocalDate monday = new LocalDate(mondayDate);
        String day;
        Double count;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
        LocalDate localDateParseFromString;
        if (!type.equals("back") && !type.equals("next") && !type.equals("current")) {
            throw new RuntimeException("Type must be 'back' or 'next'");
        }

        if (type.equals("current") || mondayDate == null) {
            monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
            sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);
        } else if (type.equals("back")) {
            monday = monday.minusWeeks(1);
            sunday = monday.plusDays(6);
        } else {
            monday = monday.plusWeeks(1);
            sunday = monday.plusDays(6);
        }
        String mondayWithOutTime = Helper.convertDateToString(monday.toDate());
        String sundayWithOutTime = Helper.convertDateToString(sunday.toDate());
        var listBookingWeek = bookingRepository.findAllByDateBookingBetween(mondayWithOutTime, sundayWithOutTime);
        Map<String, Double> totalBookingsByDay = new HashMap<>();
        for (var item : listBookingWeek) {
            localDateParseFromString = LocalDate.parse(item.getDateBooking(), formatter);
            day = localDateParseFromString.dayOfWeek().getAsText();
            if (totalBookingsByDay.get(day) == null) {
                totalBookingsByDay.put(day, 1D);
            } else {
                count = totalBookingsByDay.get(day);
                totalBookingsByDay.put(day, ++count);
            }
        }
        TotalBookingInWeek totalBookingInWeek = Helper.convertMaptoTotalBookingInWeek(totalBookingsByDay,monday);
        return totalBookingInWeek;
    }

    @Override
    public TotalBookingInYear totalBookingInYear(Integer year, String type) {

        LocalDate firstDayOfYear;
        LocalDate lastDayOfYear;
        LocalDate localDateParseFromString;
        String month;
        Double count;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");


        if(!type.equals("back") && !type.equals("next") && !type.equals("current")){
            throw new RuntimeException("Type must be 'back' or 'next' or 'current'");
        }
        if(type.equals("current") || year == null) {
            year = LocalDate.now().getYear();
        }else if(type.equals("back")) {
            year--;
        }else {
            year++;
        }
        firstDayOfYear = new LocalDate(year, 1, 1);
        lastDayOfYear = new LocalDate(year, 12, 31);
        var listBookingYear = bookingRepository.findAllByDateBookingBetween(Helper.convertDateToString(firstDayOfYear.toDate()), Helper.convertDateToString(lastDayOfYear.toDate()));
        Map<String, Double> totalBookingsByMonth = new HashMap<>();
        for (var item : listBookingYear) {
            localDateParseFromString = LocalDate.parse(item.getDateBooking(), formatter);
            month = localDateParseFromString.monthOfYear().getAsText();
            if(totalBookingsByMonth.get(month) == null){
                totalBookingsByMonth.put(month,1D);
            }else {
                count = totalBookingsByMonth.get(month);
                totalBookingsByMonth.put(month,++count);
            }
        }
        TotalBookingInYear totalBookingInYear = Helper.convertMaptoTotalBookingInYear(totalBookingsByMonth,year);

        return totalBookingInYear;
    }

    @Override
    public TotalBookingInWeek totalComissionInWeek(Date mondayDate, String type) {
        LocalDate now = new LocalDate();
        LocalDate sunday;
        LocalDate monday = new LocalDate(mondayDate);
        String day;
        Double total = 0.0;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
        LocalDate localDateParseFromString;
        if (!type.equals("back") && !type.equals("next") && !type.equals("current")) {
            throw new RuntimeException("Type must be 'back' or 'next'");
        }

        if (type.equals("current") || mondayDate == null) {
            monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
            sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);
        } else if (type.equals("back")) {
            monday = monday.minusWeeks(1);
            sunday = monday.plusDays(6);
        } else {
            monday = monday.plusWeeks(1);
            sunday = monday.plusDays(6);
        }
        String mondayWithOutTime = Helper.convertDateToString(monday.toDate());
        String sundayWithOutTime = Helper.convertDateToString(sunday.toDate());
        var listBookingWeek = bookingRepository.findAllByDateBookingBetween(mondayWithOutTime, sundayWithOutTime);
        Map<String, Double> totalBookingsByDay = new HashMap<>();
        for (var item : listBookingWeek) {
            localDateParseFromString = LocalDate.parse(item.getDateBooking(), formatter);
            day = localDateParseFromString.dayOfWeek().getAsText();
            if (totalBookingsByDay.get(day) == null) {
                totalBookingsByDay.put(day, item.getCommission());
            } else {
                total = totalBookingsByDay.get(day) + item.getCommission();
                totalBookingsByDay.put(day, total);
            }
        }
        TotalBookingInWeek totalBookingInWeek = Helper.convertMaptoTotalBookingInWeek(totalBookingsByDay,monday);


        return totalBookingInWeek;
    }

    @Override
    public TotalBookingInYear totalComissionInYear(Integer year, String type) {
        LocalDate firstDayOfYear;
        LocalDate lastDayOfYear;
        LocalDate localDateParseFromString;
        String month;
        Double total;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");


        if(!type.equals("back") && !type.equals("next") && !type.equals("current")){
            throw new RuntimeException("Type must be 'back' or 'next' or 'current'");
        }
        if(type.equals("current") || year == null) {
            year = LocalDate.now().getYear();
        }else if(type.equals("back")) {
            year--;
        }else {
            year++;
        }
        firstDayOfYear = new LocalDate(year, 1, 1);
        lastDayOfYear = new LocalDate(year, 12, 31);
        var listBookingYear = bookingRepository.findAllByDateBookingBetween(Helper.convertDateToString(firstDayOfYear.toDate()), Helper.convertDateToString(lastDayOfYear.toDate()));
        Map<String, Double> totalBookingsByMonth = new HashMap<>();
        for (var item : listBookingYear) {
            localDateParseFromString = LocalDate.parse(item.getDateBooking(), formatter);
            month = localDateParseFromString.monthOfYear().getAsText();
            if(totalBookingsByMonth.get(month) == null){
                totalBookingsByMonth.put(month,item.getCommission());
            }else {
                total = totalBookingsByMonth.get(month) + item.getCommission();
                totalBookingsByMonth.put(month, total);
            }
        }
        TotalBookingInYear totalBookingInYear = Helper.convertMaptoTotalBookingInYear(totalBookingsByMonth,year);

        return totalBookingInYear;
    }

    @Override
    public TotalBookingInWeek totalPointInWeek(Date mondayDate, String type) {
        LocalDate now = new LocalDate();
        LocalDate sunday;
        LocalDate monday = new LocalDate(mondayDate);
        String day;
        Double total = 0.0;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
        LocalDate localDateParseFromString;
        if (!type.equals("back") && !type.equals("next") && !type.equals("current")) {
            throw new RuntimeException("Type must be 'back' or 'next'");
        }

        if (type.equals("current") || mondayDate == null) {
            monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
            sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);
        } else if (type.equals("back")) {
            monday = monday.minusWeeks(1);
            sunday = monday.plusDays(6);
        } else {
            monday = monday.plusWeeks(1);
            sunday = monday.plusDays(6);
        }
        String mondayWithOutTime = Helper.convertDateToString(monday.toDate());
        String sundayWithOutTime = Helper.convertDateToString(sunday.toDate());
        var listMoneyTransferWeek = transactionRepository.findAllByPaymentDateBetweenAndStatus(mondayWithOutTime, sundayWithOutTime, EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
        Map<String, Double> totalPointByDay = new HashMap<>();
        for (var item : listMoneyTransferWeek) {
            localDateParseFromString = LocalDate.parse(item.getPaymentDate(), formatter);
            day = localDateParseFromString.dayOfWeek().getAsText();
            if (totalPointByDay.get(day) == null) {
                totalPointByDay.put(day, (double) item.getAmountCoinDeposit());
            } else {
                total = totalPointByDay.get(day) + (double) item.getAmountCoinDeposit();
                totalPointByDay.put(day, total);
            }
        }
        TotalBookingInWeek totalMoneyInWeek = Helper.convertMaptoTotalBookingInWeek(totalPointByDay,monday);

        return totalMoneyInWeek;
    }

    @Override
    public TotalBookingInYear totalPointInYear(Integer year, String type) {
        LocalDate firstDayOfYear;
        LocalDate lastDayOfYear;
        LocalDate localDateParseFromString;
        String month;
        Double total;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");


        if(!type.equals("back") && !type.equals("next") && !type.equals("current")){
            throw new RuntimeException("Type must be 'back' or 'next' or 'current'");
        }
        if(type.equals("current") || year == null) {
            year = LocalDate.now().getYear();
        }else if(type.equals("back")) {
            year--;
        }else {
            year++;
        }
        firstDayOfYear = new LocalDate(year, 1, 1);
        lastDayOfYear = new LocalDate(year, 12, 31);
        var listMoneyYear = transactionRepository.findAllByPaymentDateBetweenAndStatus(Helper.convertDateToString(firstDayOfYear.toDate()), Helper.convertDateToString(lastDayOfYear.toDate()), EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
        Map<String, Double> totalBookingsByMonth = new HashMap<>();
        for (var item : listMoneyYear) {
            localDateParseFromString = LocalDate.parse(item.getPaymentDate(), formatter);
            month = localDateParseFromString.monthOfYear().getAsText();
            if(totalBookingsByMonth.get(month) == null){
                totalBookingsByMonth.put(month,(double) item.getAmountCoinDeposit());
            }else {
                total = totalBookingsByMonth.get(month) + (double) item.getAmountCoinDeposit();
                totalBookingsByMonth.put(month, total);
            }
        }
        TotalBookingInYear totalBookingInYear = Helper.convertMaptoTotalBookingInYear(totalBookingsByMonth,year);
        return totalBookingInYear;
    }
}
