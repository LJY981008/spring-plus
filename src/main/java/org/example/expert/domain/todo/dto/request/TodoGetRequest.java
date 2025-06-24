package org.example.expert.domain.todo.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoGetRequest {
    private String weather = "";
    private LocalDate startDate = LocalDate.of(1, 1, 1);
    private LocalDate endDate = LocalDate.now();
}
