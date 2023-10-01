package com.example.holidayswap.utils;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationRequest;
import com.example.holidayswap.domain.dto.response.payment.TransactionTranferPointResponse;
import com.example.holidayswap.domain.entity.payment.AllLog;
import com.example.holidayswap.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class Helper {

    public static String getCurrentDate() {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return targetFormat.format(cld.getTime());
    }

    public static boolean checkOverlapTime(List<VacationRequest> vacationRequests) {

        for(int i = 0; i < vacationRequests.size(); i++) {
            for(int j = i + 1; j < vacationRequests.size(); j++) {
                VacationRequest vacationRequest1 = vacationRequests.get(i);
                VacationRequest vacationRequest2 = vacationRequests.get(j);
                if(vacationRequest1.getStartTime().before(vacationRequest2.getStartTime())
                        && vacationRequest1.getEndTime().after(vacationRequest2.getEndTime())) {
                    return false;
                }
                if(vacationRequest2.getStartTime().before(vacationRequest1.getStartTime())
                        && vacationRequest2.getStartTime().before(vacationRequest1.getEndTime())) {
                    return false;
                }
                if(vacationRequest2.getStartTime().before(vacationRequest1.getEndTime())
                        && vacationRequest1.getEndTime().before(vacationRequest2.getEndTime())) {
                    return false;
                }
                if(vacationRequest2.getStartTime().before(vacationRequest1.getStartTime())
                        && vacationRequest1.getEndTime().before(vacationRequest2.getEndTime())) {
                    return false;
                }
                if(vacationRequest2.getStartTime().compareTo(vacationRequest1.getStartTime()) ==0
                        && vacationRequest1.getEndTime().compareTo(vacationRequest2.getEndTime()) == 0) {
                    return false;
                }
                }
            }

        return true;
    }


}
