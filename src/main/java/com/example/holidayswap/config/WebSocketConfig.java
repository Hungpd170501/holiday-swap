package com.example.holidayswap.config;

import com.example.holidayswap.config.security.JwtService;
import com.example.holidayswap.domain.exception.VerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtService jwtService;
    @Value("${rabbitmq.host}")
    private String host;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                assert accessor != null;
                accessor.setUser(null);
                if (accessor.getCommand() == StompCommand.SEND
                        || accessor.getCommand() == StompCommand.MESSAGE
                        || accessor.getCommand() == StompCommand.SUBSCRIBE) {
                    final String accessToken = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
                    jwtService.extractUserId(accessToken);
                    if (accessToken != null) {
                        String userId = jwtService.extractUserId(accessToken);
                        accessor.setUser(() -> userId);
                    } else throw new VerificationException("Invalid token");
                }
                return message;
            }
        });

    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableStompBrokerRelay("/topic/", "/queue/", "/temp-queue/")
                .setRelayHost(host)
                .setRelayPort(61613) // RabbitMQ STOMP port
                .setClientLogin("guest") // RabbitMQ username
                .setClientPasscode("guest"); // RabbitMQ password
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOriginPatterns("*");
        registry.addEndpoint("/websocket")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
