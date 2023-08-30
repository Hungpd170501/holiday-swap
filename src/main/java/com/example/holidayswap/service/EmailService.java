package com.example.holidayswap.service;

import com.example.holidayswap.domain.dto.request.EmailRequest;
import com.example.holidayswap.rabbitmq.MQProducer;
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
        sendMessage(EmailRequest
                .builder()
                .to(email)
                .subject("HolidaySwap – Receipt of your membership")
                .template("registration-receipt")
                .attributes((Map<String, Object>) new HashMap<>().put("name", name)).build());
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
}