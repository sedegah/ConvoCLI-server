package io.convocli.ws;

import io.convocli.repository.MessageRepository;
import io.convocli.security.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final JwtUtil jwtUtil;
    private final MessageRepository messageRepository;

    public WebSocketConfig(JwtUtil jwtUtil, MessageRepository messageRepository) {
        this.jwtUtil = jwtUtil;
        this.messageRepository = messageRepository;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(jwtUtil, messageRepository), "/ws/chat")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new DefaultHandshakeHandler());
    }
}
