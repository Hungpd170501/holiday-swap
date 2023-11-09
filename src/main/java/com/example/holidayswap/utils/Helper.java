package com.example.holidayswap.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
}
