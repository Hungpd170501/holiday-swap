package com.example.holidayswap.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service("smsService")
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {
    private static final String SENDER_ID = "HolidaySwap";
    private static final SecureRandom random = new SecureRandom();
    private final AmazonSNSClient amazonSNSClient;

    public static String getRandomNumberString() {
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
        MessageAttributeValue senderId = new MessageAttributeValue()
                .withStringValue(SENDER_ID) //The sender ID shown on the device.
                .withDataType("String");
        MessageAttributeValue maxPrice = new MessageAttributeValue()
                .withStringValue("0.50") //Sets the max price to 0.30 USD.
                .withDataType("Number");
        MessageAttributeValue smsType = new MessageAttributeValue()
                .withStringValue("Transactional")
                .withDataType("String");
        smsAttributes.put("AWS.SNS.SMS.SenderID", senderId);
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", maxPrice);
        smsAttributes.put("AWS.SNS.SMS.SMSType", smsType);
        PublishRequest publishRequest = new PublishRequest()
                .withMessageAttributes(smsAttributes)
                .withMessage(message)
                .withPhoneNumber(phoneNumber);
        amazonSNSClient.publish(publishRequest);
    }

    @Override
    public void send6DigitCode(String phoneNumber) {
        String code = getRandomNumberString();
        sendSms(phoneNumber, "%s is your HolidaySwap verification code.".formatted(code));
    }
}
