package com.meu.news_crawler_be.logging;

import com.meu.news_crawler_be.config.AppLogger;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ConfigLogger {
    private final Logger LOGGER = AppLogger.LOGGER;

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
