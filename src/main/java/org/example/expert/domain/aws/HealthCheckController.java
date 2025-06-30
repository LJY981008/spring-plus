package org.example.expert.domain.aws;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public String checkHealth() {
        return "ok"; // "ok" 문자열을 반환하여 서버가 정상 동작 중임을 알립니다.
    }
}
