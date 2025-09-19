package io.convocli.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages", indexes = {@Index(name="idx_created_at", columnList = "createdAt")})
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;
    private String receiver;

    @Column(columnDefinition="text")
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Message() {}
    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
