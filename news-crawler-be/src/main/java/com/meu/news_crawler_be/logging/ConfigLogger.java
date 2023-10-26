package com.meu.news_crawler_be.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ConfigLogger {
    private final Logger LOGGER = LoggerFactory.getLogger(ConfigLogger.class);

    @Pointcut("execution(public * com.meu.news_crawler_be.config.*.*(..))")
    public void configMethods() {}

    @AfterThrowing(value = "configMethods()", throwing = "e")
    public void configLogging(JoinPoint joinPoint, Exception e) {
        LOGGER.warn(String.format("```yaml\n!##EXCEPTION: %s.%s() !ARGS: %s!```",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())));
        LOGGER.error("||[" + e + "]||");
    }
}
