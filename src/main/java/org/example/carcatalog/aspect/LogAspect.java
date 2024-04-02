package org.example.carcatalog.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
public class LogAspect {
    private final Logger MY_LOGGER = LogManager.getLogger(this.getClass());

    @Pointcut("@annotation(AspectAnnotation)")
    public void methodsWithAspectAnnotation() {
    }

    @Pointcut("execution(* org.example.carcatalog.service.*.*(..))"
            /*+ "|| execution(* org.example.carcatalog.controller.*.*(..))"*/)
    public void callAllMethods() {
    }

    @Before("callAllMethods()")
    public void logAfterReturning(final JoinPoint jp) {
        MY_LOGGER.info(MessageFormat.format("Method {0}.{1}() was called",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName()));
    }

    @AfterReturning(pointcut = "methodsWithAspectAnnotation()",
            returning = "result")
    public void logMethodReturn(final JoinPoint joinPoint,
                                final Object result) {
        MY_LOGGER.info(MessageFormat.format(
                "Method is returned: {0}.{1}() , returned value: {2} ",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result.toString()));
    }

    @AfterThrowing(pointcut = "callAllMethods()", throwing = "exception")
    public void logException(final JoinPoint jp, final Throwable exception) {
        MY_LOGGER.info(MessageFormat.format("Exception in {0}.{1}(),cause: {2}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(), exception.getMessage()));
    }
}




