package org.example.carcatalog.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.carcatalog.counter.CounterService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final CounterService counterService = CounterService.getCounterService();

    @Pointcut("@annotation(CounterAspect)")
    public void methodsToCount() {
    }

    @Pointcut("@annotation(AspectAnnotation)")
    public void methodsWithAspectAnnotation() {
    }

    @Pointcut("execution(* org.example.carcatalog.service.*.*(..))"
            + "|| execution(* org.example.carcatalog.advice.*.*(..))"
            + "|| execution(* org.example.carcatalog.controller.*.*(..))")
    public void callAllMethods() {
    }
    @Before("methodsToCount()")
    public void incrementCounter() {
        counterService.incrementCounter();
        System.out.println(counterService.getCounter());
    }

    @Before("callAllMethods()")
    public void logBeforeMethod(final JoinPoint jp) {
        logger.info("Method {}.{}() was called",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName());
    }

    @AfterReturning(pointcut = "methodsWithAspectAnnotation()",
            returning = "result")
    public void logMethodReturn(final JoinPoint jp,
                                final Object result) {
        logger.info("Method is returned: {}.{}(), returned value: {} ",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "callAllMethods()", throwing = "exception")
    public void logException(final JoinPoint jp, final Throwable exception) {
        logger.info("Exception in {}.{}(), cause: {}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(), exception.getMessage());
    }
}




