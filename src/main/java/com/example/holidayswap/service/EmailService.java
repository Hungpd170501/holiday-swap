package com.example.holidayswap.service;

import com.example.holidayswap.domain.dto.request.EmailRequest;
import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.rabbitmq.MQProducer;
import com.example.holidayswap.utils.Helper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final MQProducer rabbitMQMessageProducer;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${application.base-url}")
    private String baseUrl;

    public void sendRegistrationReceipt(String email, String name) {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("name", name);
        sendMessage(EmailRequest
                .builder()
                .to(email)
                .subject("HolidaySwap – Receipt of your membership")
                .template("registration-receipt")
                .attributes(attribute).build());
    }

    public void sendForgotPasswordEmail(String email, String token) {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("baseUrl", baseUrl + "/api/v1/auth/forgot-password");
        attribute.put("verificationToken", token);
        sendMessage(EmailRequest
                .builder()
                .to(email)
                .subject("HolidaySwap - Reset your password")
                .template("reset-password")
                .attributes(attribute).build());
    }


    public void sendVerificationEmail(String email, String token) {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("baseUrl", baseUrl + "/api/v1/auth/verify-email");
        attribute.put("verificationToken", token);
        sendMessage(EmailRequest
                .builder()
                .to(email)
                .subject("HolidaySwap - Please verify your email address")
                .template("email-verification")
                .attributes(attribute).build());
    }

    public void sendNotificationRegisterCoOwnerSuccessEmail(String email, String name) {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("name", name);
        sendMessage(EmailRequest
                .builder()
                .to(email)
                .subject("HolidaySwap - Your register co-owner in apartment is now accepted")
                .template("register-coowner-success")
                .attributes(attribute).build());
    }

    public void sendNotificationRegisterCoOwnerDeclineEmail(String email, String name) {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("name", name);
        sendMessage(EmailRequest
                .builder()
                .to(email)
                .subject("HolidaySwap - Your register co-owner in apartment has been Rejected")
                .template("registration-decline")
                .attributes(attribute).build());
    }

    public void sendVerificationCode(String email, String name, String code) {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("name", name);
        attribute.put("code", code);
        sendMessage(EmailRequest
                .builder()
                .to(email)
                .subject("HolidaySwap – Verification Code")
                .template("verification-code")
                .attributes(attribute).build());
    }


    public void sendMessage(EmailRequest emailRequest) {
        rabbitMQMessageProducer.publish(
                emailRequest,
                "internal.exchange",
                "internal.email.routing-key"
        );
    }

    public void sendMessageHtml(EmailRequest emailRequest) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(emailRequest.getAttributes());
        String htmlBody = thymeleafTemplateEngine.process(emailRequest.getTemplate(), thymeleafContext);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(username);
        helper.setTo(emailRequest.getTo());
        helper.setSubject(emailRequest.getSubject());
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }

    public void sendConfirmBookedHtml(Booking booking, String email) throws MessagingException {
        ;
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("qrCode", booking.getQrcode());
        attribute.put("propertyName", booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getPropertyName());
        attribute.put("resortName", booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getResort().getResortName());
        attribute.put("owner", booking.getAvailableTime().getTimeFrame().getCoOwner().getUser().getUsername());
        attribute.put("checkInDate", Helper.convertDateToString(booking.getCheckInDate()));
        attribute.put("checkOutDate", Helper.convertDateToString(booking.getCheckOutDate()));
        attribute.put("bookingDate", booking.getDateBooking());
        sendMessage(EmailRequest
                .builder()
                .to(email)
                .subject("Confirmed Booking")
                .template("booking-response")
                .attributes(attribute).build());
    }
}

