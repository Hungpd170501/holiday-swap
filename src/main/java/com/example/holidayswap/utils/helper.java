package com.example.holidayswap.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class helper {
    public static String getCurrentDate() {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return targetFormat.format(cld.getTime());
    }
}
