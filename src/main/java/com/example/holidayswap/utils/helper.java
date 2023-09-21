package com.example.holidayswap.utils;

import com.example.holidayswap.domain.dto.response.payment.TransactionTranferPointResponse;
import com.example.holidayswap.domain.entity.payment.AllLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class helper {
    public static String getCurrentDate() {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return targetFormat.format(cld.getTime());
    }

    public static List<TransactionTranferPointResponse> convertAllLogToTransactionTranferPointResponse(List<AllLog> allLogs) {
        return allLogs.stream().forEach(allLog -> {
            TransactionTranferPointResponse transactionTranferPointResponse = new TransactionTranferPointResponse();
            transactionTranferPointResponse.setId(allLog.getId());
            transactionTranferPointResponse.setFrom(String.valueOf(allLog.getFromID()));
            transactionTranferPointResponse.setTo(String.valueOf(allLog.getToID()));
            transactionTranferPointResponse.setAmount(allLog.getAmount());
            transactionTranferPointResponse.setTotalPoint(allLog.getFromUser().getWallet().getTotalPoint());

            // khi xong giao dich thi ghi lai so du cua vi cua 2 ben
            transactionTranferPointResponse.setStatus(allLog.getStatus().name());
            transactionTranferPointResponse.setDate(allLog.getDate());
        });
    }
}
