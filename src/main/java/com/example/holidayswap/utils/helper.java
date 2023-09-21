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

public class helper {
    @Autowired
    private static UserService userService;
    public static String getCurrentDate() {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return targetFormat.format(cld.getTime());
    }

    public static List<TransactionTranferPointResponse> convertAllLogToTransactionTranferPointResponse(List<AllLog> allLogs, Long userId) {
        return allLogs.stream().map(allLog -> {
            TransactionTranferPointResponse transactionTranferPointResponse = new TransactionTranferPointResponse();
            transactionTranferPointResponse.setId(allLog.getId());
            transactionTranferPointResponse.setFrom(userService.getUserById(allLog.getFromID()).getUsername());
            transactionTranferPointResponse.setTo(userService.getUserById(allLog.getToID()).getUsername());

            if(allLog.getFromID() == userId) {
                transactionTranferPointResponse.setTotalPoint(allLog.getFromBalance());
                transactionTranferPointResponse.setAmount("+" + allLog.getAmount());
            } else {
                transactionTranferPointResponse.setTotalPoint(allLog.getToBalance());
                transactionTranferPointResponse.setAmount("-" + allLog.getAmount());
            }

            // khi xong giao dich thi ghi lai so du cua vi cua 2 ben
            transactionTranferPointResponse.setStatus(allLog.getResultCode().name());
            transactionTranferPointResponse.setDate(allLog.getCreatedOn());
            return transactionTranferPointResponse;

        }).collect(Collectors.toList());
    }
}
