package org.example.expert;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(properties = {"spring.profiles.active=test"})
public class UserInsertTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void insertMillionUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            String nickname = "Test Name-" + i; // 중복 최소화
            User user = new User("user" + i + "@test.com", nickname, "password", UserRole.USER);
            users.add(user);
            if (users.size() == 10_000) { // 1만 건씩 배치 저장
                userRepository.saveAll(users);
                users.clear();
            }
        }
        if (!users.isEmpty()) {
            userRepository.saveAll(users);
        }
    }
}
