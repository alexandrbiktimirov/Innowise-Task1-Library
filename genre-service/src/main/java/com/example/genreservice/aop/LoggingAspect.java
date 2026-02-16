package com.example.genreservice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.library.service..*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        String paramTypes = Arrays.stream(signature.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(","));
        String paramValues = Arrays.stream(joinPoint.getArgs())
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        String callFact = className + "." + methodName + "(" + paramTypes + "," + paramValues + ")";

        logger.info("Entering {} with args=[{}]", callFact, paramValues);

        try {
            Object result = joinPoint.proceed();

            String resultStr = safeToString(result);

            logger.info("Exiting {} with result=[{}]", callFact, resultStr);

            return result;
        } catch( Throwable e ) {
            logger.error("Throwing {} with com.example.library.exception={} message={}", callFact, e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }

    private String safeToString(Object object) {
        if (object == null) {
            return "null";
        }

        String result = String.valueOf(object);
        return result.length() > 500 ? result.substring(0, 500) + "...(truncated)" : result;
    }
}
