package io.convocli.controller;

import io.convocli.model.Message;
import io.convocli.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageRepository repo;
    public MessageController(MessageRepository repo) { this.repo = repo; }

    @GetMapping("/history")
    public List<Message> history(@RequestParam String withUser) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        return repo.findAll().stream()
                .filter(m -> (m.getSender().equals(withUser) || m.getReceiver().equals(withUser)) && m.getCreatedAt().isAfter(cutoff))
                .sorted((a,b)->a.getCreatedAt().compareTo(b.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
