package com.example.holidayswap.domain.entity.payment;

import lombok.Getter;
import lombok.Setter;

public class EnumPaymentStatus {
    public enum StatusPoint {
        ACTIVE,INACTIVE ;
    }
    public enum StatusMoneyTranfer {
        WAITING,SUCCESS,FAILED ;
    }

    public enum BankCodeError {
        SUCCESS,
        UNKNOWN_ERROR,
        ACCOUNT_DISABLED,
        ID_NOT_FOUND,
        BALANCE_NOT_ENOUGH,
        DATABASE_ERROR;

    }

    public enum StatusPointTransfer{
        POINT_TRANSFER, POINT_RECEIVE;
    }
}
