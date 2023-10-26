package com.meu.news_crawler_be.logging;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
@Component
public class ConverterLogger {
    private final Logger LOGGER = LoggerFactory.getLogger(ConverterLogger.class);

    @Pointcut("execution(public * com.meu.news_crawler_be.converter.*.*(..))")
    public void converterMethods() {}

    @AfterThrowing(value = "converterMethods()", throwing = "e")
    public void converterLogging(JoinPoint joinPoint, Exception e) {
        LOGGER.warn(String.format("```yaml\n!##EXCEPTION: %s.%s() !ARGS: %s!```",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())));
        LOGGER.error("||[" + e + "]||");
    }
}
