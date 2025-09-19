package io.convocli.service;

import io.convocli.repository.MessageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PruneService {

    private final MessageRepository repo;
    public PruneService(MessageRepository repo) { this.repo = repo; }

    // run daily at 1:00
    @Scheduled(cron = "0 0 1 * * *")
    public void pruneOldMessages() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        repo.deleteByCreatedAtBefore(cutoff);
    }
}
