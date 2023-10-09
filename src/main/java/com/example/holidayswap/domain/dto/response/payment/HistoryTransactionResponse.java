package com.example.holidayswap.domain.dto.response.payment;

import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import lombok.Data;

import java.util.Date;

@Data
public class HistoryTransactionResponse {
    private String from;
    private String to;
    private String amount;
    private Date createdOn;
    private String message;
    private Double totalPoint;
    private EnumPaymentStatus.TransactionStatus type;
    private EnumPaymentStatus.StatusMoneyTranfer status;
    private String dateConvert;
}
