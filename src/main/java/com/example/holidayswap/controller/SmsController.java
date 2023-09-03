package com.example.holidayswap.controller;

import com.example.holidayswap.service.SmsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sms")
public class SmsController {
    private final SmsService smsService;

    public SmsController(@Qualifier("smsService2") SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send-opt")
    public void sendOpt(@RequestBody String phoneNumber) {
        smsService.send6DigitCode(phoneNumber);
    }

    @PostMapping("/send-sms")
    public void sendSms(@RequestBody String phoneNumber,
                        @RequestBody String message) {
        smsService.sendSms(phoneNumber, message);
    }
}
