package com.example.holidayswap.utils;

import com.example.holidayswap.domain.dto.response.reportdashboard.TotalBookingInWeek;
import com.example.holidayswap.domain.dto.response.reportdashboard.TotalBookingInYear;
import org.joda.time.LocalDate;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class Helper {

    public static String getCurrentDate() {
        ZonedDateTime hcmZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return hcmZonedDateTime.format(formatter);
    }
    public static String getCurrentDateWithoutTime() {
        ZonedDateTime hcmZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return hcmZonedDateTime.format(formatter);
    }
    public static String convertDateToString(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return date.toInstant().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).format(formatter);
    }
    public static String convertDateAndSplitDayToString(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
        return date.toInstant().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).format(formatter);
    }
    public static String getMonthAndYearCurrent() {
        ZonedDateTime hcmZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
        return hcmZonedDateTime.format(formatter);
    }
    public static TotalBookingInYear convertMaptoTotalBookingInYear(Map<String,Double> totalBookingsByMonth,Integer year) {
        TotalBookingInYear totalBookingInYear = new TotalBookingInYear();
        totalBookingInYear.setTotalBookingInJanuary(totalBookingsByMonth.get("January") == null ? 0 : totalBookingsByMonth.get("January"));
        totalBookingInYear.setTotalBookingInFebruary(totalBookingsByMonth.get("February") ==null? 0 :totalBookingsByMonth.get("February"));
        totalBookingInYear.setTotalBookingInMarch(totalBookingsByMonth.get("March") ==null? 0 :totalBookingsByMonth.get("March"));
        totalBookingInYear.setTotalBookingInApril(totalBookingsByMonth.get("April") ==null? 0 :totalBookingsByMonth.get("April"));
        totalBookingInYear.setTotalBookingInMay(totalBookingsByMonth.get("May") ==null? 0 :totalBookingsByMonth.get("May"));
        totalBookingInYear.setTotalBookingInJune(totalBookingsByMonth.get("June") ==null? 0 :totalBookingsByMonth.get("June"));
        totalBookingInYear.setTotalBookingInJuly(totalBookingsByMonth.get("July") ==null? 0 :totalBookingsByMonth.get("July"));
        totalBookingInYear.setTotalBookingInAugust(totalBookingsByMonth.get("August") ==null? 0 :totalBookingsByMonth.get("August"));
        totalBookingInYear.setTotalBookingInSeptember(totalBookingsByMonth.get("September") ==null? 0 :totalBookingsByMonth.get("September"));
        totalBookingInYear.setTotalBookingInOctober(totalBookingsByMonth.get("October") ==null? 0 :totalBookingsByMonth.get("October"));
        totalBookingInYear.setTotalBookingInNovember(totalBookingsByMonth.get("November") ==null? 0 :totalBookingsByMonth.get("November"));
        totalBookingInYear.setTotalBookingInDecember(totalBookingsByMonth.get("December") ==null? 0 :totalBookingsByMonth.get("December"));
        totalBookingInYear.setYear(year);
        return totalBookingInYear;
    }

    public static TotalBookingInWeek convertMaptoTotalBookingInWeek(Map<String,Double> totalPointByDay, LocalDate monday){
        TotalBookingInWeek totalMoneyInWeek = new TotalBookingInWeek();
        totalMoneyInWeek.setTotalBookingInMonday(totalPointByDay.get("Monday") == null ? 0 : totalPointByDay.get("Monday"));
        totalMoneyInWeek.setTotalBookingInTuesday(totalPointByDay.get("Tuesday") == null ? 0 : totalPointByDay.get("Tuesday"));
        totalMoneyInWeek.setTotalBookingInWednesday(totalPointByDay.get("Wednesday") == null ? 0 : totalPointByDay.get("Wednesday"));
        totalMoneyInWeek.setTotalBookingInThursday(totalPointByDay.get("Thursday") == null ? 0 : totalPointByDay.get("Thursday"));
        totalMoneyInWeek.setTotalBookingInFriday(totalPointByDay.get("Friday") == null ? 0 : totalPointByDay.get("Friday"));
        totalMoneyInWeek.setTotalBookingInSaturday(totalPointByDay.get("Saturday") == null ? 0 : totalPointByDay.get("Saturday"));
        totalMoneyInWeek.setTotalBookingInSunday(totalPointByDay.get("Sunday") == null ? 0 : totalPointByDay.get("Sunday"));
        totalMoneyInWeek.setMondayDate(monday.toDate());
        return totalMoneyInWeek;
    }
}
