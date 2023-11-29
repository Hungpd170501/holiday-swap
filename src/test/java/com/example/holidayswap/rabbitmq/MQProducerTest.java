package com.example.holidayswap.rabbitmq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MQProducerTest {

    @Mock
    private AmqpTemplate mockAmqpTemplate;

    private MQProducer mqProducerUnderTest;

    @BeforeEach
    void setUp() {
        mqProducerUnderTest = new MQProducer(mockAmqpTemplate);
    }

    @Test
    void testPublish() {
        // Setup
        // Run the test
        mqProducerUnderTest.publish("payload", "exchange", "routingKey");

        // Verify the results
        verify(mockAmqpTemplate).convertAndSend(eq("exchange"), eq("routingKey"), any(Object.class));
    }

    @Test
    void testPublish_AmqpTemplateThrowsAmqpException() {
        // Setup
        doThrow(AmqpException.class).when(mockAmqpTemplate).convertAndSend(eq("exchange"), eq("routingKey"),
                any(Object.class));

        // Run the test
        assertThatThrownBy(() -> mqProducerUnderTest.publish("payload", "exchange", "routingKey"))
                .isInstanceOf(AmqpException.class);
    }
}
