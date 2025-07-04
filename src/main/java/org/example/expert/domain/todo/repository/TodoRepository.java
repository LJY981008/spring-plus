package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u " +
            "WHERE t.weather LIKE '%' || :weather || '%'" +
            "AND t.modifiedAt >= :startDate AND t.modifiedAt <= :endDate " +
            "ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable, String weather, LocalDateTime startDate, LocalDateTime endDate);

//    @Query("SELECT t FROM Todo t " +
//            "LEFT JOIN t.user " +
//            "WHERE t.id = :todoId")
//    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
