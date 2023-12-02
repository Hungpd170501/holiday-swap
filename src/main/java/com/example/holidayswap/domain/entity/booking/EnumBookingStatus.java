package com.example.holidayswap.domain.entity.booking;

public class EnumBookingStatus {
    public enum BookingStatus {
        PENDING,
        ACCEPTED,
        REJECTED,
        CANCELLED,
        EXPIRED,
        SUCCESS,
        FAILED,
    }
    public enum TransferStatus{
        WAITING,
        SUCCESS,
        REFUND_USER
    }
}
