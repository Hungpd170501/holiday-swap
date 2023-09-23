package com.example.holidayswap.utils;

import com.example.holidayswap.domain.dto.response.payment.TransactionTranferPointResponse;
import com.example.holidayswap.domain.entity.payment.AllLog;
import com.example.holidayswap.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class Helper {

    public static String getCurrentDate() {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return targetFormat.format(cld.getTime());
    }


}
