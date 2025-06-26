package org.example.expert.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(Long userId, Long todoId, LogAction action, String logMessage) {
        Log log = new Log(todoId, userId, action, logMessage);
        logRepository.save(log);
    }
}
