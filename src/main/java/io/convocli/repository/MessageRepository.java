package io.convocli.repository;

import io.convocli.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiverAndCreatedAtAfterOrderByCreatedAtAsc(String sender, String receiver, LocalDateTime after);
    List<Message> findByReceiverAndSenderAndCreatedAtAfterOrderByCreatedAtAsc(String receiver, String sender, LocalDateTime after);
    List<Message> findBySenderAndCreatedAtAfterOrderByCreatedAtAsc(String sender, LocalDateTime after);
    List<Message> findByReceiverAndCreatedAtAfterOrderByCreatedAtAsc(String receiver, LocalDateTime after);
    void deleteByCreatedAtBefore(LocalDateTime cutoff);
}
