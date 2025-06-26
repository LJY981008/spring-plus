package org.example.expert.domain.todo.dto.request;

import java.time.LocalDate;
import java.util.Optional;

public class TodoSearchRequest {
    private String likeTitle;
    private String likeManagerName;
    private LocalDate startDate;
    private LocalDate endDate;
    
    public Optional<String> getLikeTitleOptional() {
        return Optional.ofNullable(likeTitle);
    }
    
    public Optional<String> getLikeManagerNameOptional() {
        return Optional.ofNullable(likeManagerName);
    }
    
    public Optional<LocalDate> getStartDateOptional() {
        return Optional.ofNullable(startDate);
    }
    
    public Optional<LocalDate> getEndDateOptional() {
        return Optional.ofNullable(endDate);
    }
}
