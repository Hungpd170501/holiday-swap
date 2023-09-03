package com.example.holidayswap.rabbitmq;

import com.example.holidayswap.domain.dto.request.EmailRequest;
import com.example.holidayswap.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MQConsumer {
    private final EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queues.email}")
    public void consumer(EmailRequest emailRequest) throws MessagingException {
        log.info("Consumed {} from queue", emailRequest);
        emailService.sendMessageHtml(emailRequest);
    }
}