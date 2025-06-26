package org.example.expert.log;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.expert.config.JwtUtil;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.manager.dto.response.ManagerSaveResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogAspect {

    private final LogService logService;

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    @Around("@annotation(org.example.expert.log.ManagerAOP)")
    public Object executeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LogAction action = signature.getMethod().getAnnotation(ManagerAOP.class).action();
        Object[] args = joinPoint.getArgs();

        AuthUser user = (AuthUser) args[0];
        Long userId = user.getId();
        Long todoId = (Long) args[1];

        try {
            Object result = joinPoint.proceed();

            String successMessage = String.format("Action : %s, UserID: %d, TodoId : %d, Status: SUCCESS", 
                    action.name(), userId, todoId);
            logService.saveLog(userId, todoId, action, successMessage);
            
            return result;
        } catch (Exception e) {
            String errorMessage = String.format("Action : %s, UserID: %d, TodoId : %d, Status: FAILED, Error: %s", 
                    action.name(), userId, todoId, e.getMessage());
            logService.saveLog(userId, todoId, action, errorMessage);

            throw e;
        }
    }

}
