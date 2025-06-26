package org.example.expert.log;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.expert.domain.common.entity.Timestamped;

@Entity
@Getter
@Table(name = "log")
public class Log extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long todoId;
    private Long managerId;
    private LogAction action;
    private String message;

    public Log(Long todoId, Long managerId, LogAction action, String message) {
        this.todoId = todoId;
        this.managerId = managerId;
        this.action = action;
        this.message = message;
    }

    public Log() {

    }
}
