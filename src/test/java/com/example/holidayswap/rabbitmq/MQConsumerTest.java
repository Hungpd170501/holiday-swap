package com.example.holidayswap.rabbitmq;

import com.example.holidayswap.domain.dto.request.EmailRequest;
import com.example.holidayswap.service.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MQConsumerTest {

    @Mock
    private EmailService mockEmailService;

    private MQConsumer mqConsumerUnderTest;

    @BeforeEach
    void setUp() {
        mqConsumerUnderTest = new MQConsumer(mockEmailService);
    }

    @Test
    void testConsumer() throws Exception {
        // Setup
        final EmailRequest emailRequest = EmailRequest.builder().build();

        // Run the test
        mqConsumerUnderTest.consumer(emailRequest);

        // Verify the results
        verify(mockEmailService).sendMessageHtml(EmailRequest.builder().build());
    }

    @Test
    void testConsumer_EmailServiceThrowsMessagingException() throws Exception {
        // Setup
        final EmailRequest emailRequest = EmailRequest.builder().build();
        doThrow(MessagingException.class).when(mockEmailService).sendMessageHtml(EmailRequest.builder().build());

        // Run the test
        assertThatThrownBy(() -> mqConsumerUnderTest.consumer(emailRequest)).isInstanceOf(MessagingException.class);
    }
}
