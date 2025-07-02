package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    User findByUsername(String username);
    
    // 프로젝션을 사용한 쿼리 - 필요한 컬럼만 조회
    @Query("SELECT new org.example.expert.domain.user.dto.response.UserResponse(u.id, u.email, u.profileImageUrl) FROM User u WHERE u.username = :username")
    Optional<UserResponse> findUserResponseByUsername(@Param("username") String username);
}
