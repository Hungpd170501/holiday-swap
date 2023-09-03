package com.example.holidayswap.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service("smsService2")
public class SmsServiceImpl2 implements SmsService {
    private static final SecureRandom random = new SecureRandom();
    @Value("${twilio.account_sid}")
    private String accountSid;
    @Value("${twilio.auth_token}")
    private String authToken;
    @Value("${twilio.trial_number}")
    private String trialNumber;

    public static String getRandomNumberString() {
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(trialNumber),
                message
        ).create();
    }

    @Override
    public void send6DigitCode(String phoneNumber) {
        String code = getRandomNumberString();
        sendSms(phoneNumber, "%s is your HolidaySwap verification code.".formatted(code));
    }

}
