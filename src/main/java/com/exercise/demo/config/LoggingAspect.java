package com.exercise.demo.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.exercise.demo.*.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Entering method: " + joinPoint.getSignature().toShortString());
    }

//    @After("execution(* com.exercise.demo.*.*.*(..))")
//    public void logAfter(JoinPoint joinPoint) {
//        log.info("Exiting method: " + joinPoint.getSignature().toShortString());
//    }

    @Around("execution(* com.exercise.demo.controller.*.*(..))")
    public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().toString();
        Object[] args = joinPoint.getArgs();
        String argsAsJson = objectMapper.writeValueAsString(args);
        log.info("Entering "+className+ ": method "+methodName+"() "+" arguments: "+argsAsJson);
        Object result = joinPoint.proceed();
        log.info("Response "+className+ ": method "+methodName+"() "+" result: "+objectMapper.writeValueAsString(result));
        return result;
    }
}