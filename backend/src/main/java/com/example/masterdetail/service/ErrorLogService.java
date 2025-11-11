package com.example.masterdetail.service;

import com.example.masterdetail.entity.ErrorLog;
import com.example.masterdetail.repository.ErrorLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErrorLogService {
    private final ErrorLogRepository repo;

    public ErrorLogService(ErrorLogRepository repo) {
        this.repo = repo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logError(String entity, String message) {
        ErrorLog log = ErrorLog.builder()
                .entity(entity)
                .message(message)
                .build();
        repo.save(log);
    }
}
