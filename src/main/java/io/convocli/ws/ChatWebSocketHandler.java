package io.convocli.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.convocli.model.Message;
import io.convocli.repository.MessageRepository;
import io.convocli.security.JwtUtil;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Simple WebSocket handler: clients connect to /ws/chat with Authorization header.
 * The handler stores sessions by username (from token) and forwards messages to recipient if online.
 * Messages are JSON: { "to":"username", "content":"..." }
 */
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final JwtUtil jwtUtil;
    private final MessageRepository messageRepo;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Logger logger = Logger.getLogger(ChatWebSocketHandler.class.getName());

    public ChatWebSocketHandler(JwtUtil jwtUtil, MessageRepository messageRepo) {
        this.jwtUtil = jwtUtil;
        this.messageRepo = messageRepo;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String auth = session.getHandshakeHeaders().getFirst("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Missing Authorization"));
            return;
        }
        String token = auth.substring(7);
        if (!jwtUtil.validateToken(token)) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Invalid token"));
            return;
        }
        String username = jwtUtil.getUsernameFromToken(token);
        session.getAttributes().put("username", username);
        sessions.put(username, session);
        logger.info("Connected: " + username);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<?,?> map = mapper.readValue(payload, Map.class);
        String to = (String) map.get("to");
        String content = (String) map.get("content");
        String from = (String) session.getAttributes().get("username");
        if (to == null || content == null) return;
        // persist
        Message m = new Message(from, to, content);
        messageRepo.save(m);
        // send to recipient if online
        WebSocketSession rec = sessions.get(to);
        Map<String,Object> out = Map.of("from", from, "to", to, "content", content, "createdAt", m.getCreatedAt().toString());
        String json = mapper.writeValueAsString(out);
        if (rec != null && rec.isOpen()) {
            rec.sendMessage(new TextMessage(json));
        }
        // echo back to sender for confirmation
        session.sendMessage(new TextMessage(json));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object u = session.getAttributes().get("username");
        if (u != null) sessions.remove((String) u);
    }
}
