package org.example.expert.domain.todo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import org.example.expert.domain.todo.entity.Todo;

@Getter
public class TodoSearchResponse {
    private String title;
    private Long managerCnt;
    private Long commentCnt;

    @QueryProjection
    public TodoSearchResponse(String title, Long managerCnt, Long commentCnt) {
        this.title = title;
        this.managerCnt = managerCnt;
        this.commentCnt = commentCnt;
    }
    public TodoSearchResponse() {}
}
