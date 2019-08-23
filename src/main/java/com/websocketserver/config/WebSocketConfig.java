package com.websocketserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Note: Using queue, not topic, Spring always using queue with sendToUser
        registry.enableSimpleBroker("/queue");
        /*
          Check the STOMP documentation for your message broker of choice (e.g. RabbitMQ, ActiveMQ, etc.),
          install the broker, and run it with STOMP support enabled.
          Then enable the STOMP broker relay in the Spring configuration instead of the
          simple broker.
         */
        /*registry.enableStompBrokerRelay("/topic", "/queue")
        .setRelayHost("localhost")
        .setRelayPort(61613)
        .setClientLogin("guest")
        .setClientPasscode("guest");*/
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/user-websocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
