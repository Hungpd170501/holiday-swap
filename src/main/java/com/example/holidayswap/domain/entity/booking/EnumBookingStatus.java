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
        WAITING_EXCHANGE
    }
     public enum TransferStatus{
         WAITING,
         SUCCESS,
         REFUND_USER
     }
     public enum IssueBookingStatus{
         OPEN,
         RESOLVE,
         REFUND
     }
     public enum TypeOfBooking{
         EXCHANGE,
         RENT
     }
}
