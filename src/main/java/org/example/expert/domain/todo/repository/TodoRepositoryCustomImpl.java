package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.request.TodoSearchRequest;
import org.example.expert.domain.todo.dto.response.QTodoSearchResponse;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TodoRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        QTodo todo = QTodo.todo;
        QUser user = QUser.user;

        return Optional.ofNullable(queryFactory.selectFrom(todo)
                .leftJoin(todo.user, user)
                .fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne());
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(Pageable pageable, TodoSearchRequest requestDto) {
        QTodo todo = QTodo.todo;
        QUser user = QUser.user;
        QManager manager = QManager.manager;
        QComment comment = QComment.comment;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        LocalDate effectiveStartDate = requestDto.getStartDateOptional()
                .orElse(LocalDate.of(1, 1, 1));
        LocalDate effectiveEndDate = requestDto.getEndDateOptional()
                .orElse(LocalDate.now());
        
        booleanBuilder.and(todo.createdAt.between(
            effectiveStartDate.atStartOfDay(), 
            effectiveEndDate.atTime(23, 59, 59)
        ));

        requestDto.getLikeTitleOptional()
                .filter(title -> !title.trim().isEmpty())
                .ifPresent(title -> booleanBuilder.and(todo.title.likeIgnoreCase("%" + title + "%")));

        requestDto.getLikeManagerNameOptional()
                .filter(managerName -> !managerName.trim().isEmpty())
                .ifPresent(managerName -> booleanBuilder.and(todo.id.in(
                    JPAExpressions
                        .select(manager.todo.id)
                        .from(manager)
                        .join(manager.user, user)
                        .where(user.username.likeIgnoreCase("%" + managerName + "%"))
                )));

        List<TodoSearchResponse> fetch = queryFactory
                .select(new QTodoSearchResponse(
                        todo.title,
                        JPAExpressions
                                .select(manager.count())
                                .from(manager)
                                .where(manager.todo.eq(todo)),
                        JPAExpressions
                                .select(comment.count())
                                .from(comment)
                                .where(comment.todo.eq(todo))
                ))
                .from(todo)
                .where(booleanBuilder)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = Optional.ofNullable(queryFactory
                .select(todo.count())
                .from(todo)
                .where(booleanBuilder)
                .fetchOne()).orElse(0L);

        return new PageImpl<>(fetch, pageable, totalCount);
    }
}