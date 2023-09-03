package com.example.holidayswap.service;

public interface SmsService {
    void sendSms(String phoneNumber, String message);

    void send6DigitCode(String phoneNumber);
}
