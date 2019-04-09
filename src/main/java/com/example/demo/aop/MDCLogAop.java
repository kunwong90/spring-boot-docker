package com.example.demo.aop;

import com.example.demo.util.UUIDUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
public class MDCLogAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(MDCLogAop.class);

    private static final String TRACE_ID = "traceId";

    private ThreadLocal<ProceedingJoinPoint> beforeThreadLocal = new ThreadLocal<>();

    @Pointcut("execution(* com.example.demo..*(..))")
    public void mdcLog() {

    }

    @Around("mdcLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        if (beforeThreadLocal.get() == null) {
            beforeThreadLocal.set(pjp);
        }
        try {
            String traceId = MDC.get(TRACE_ID);
            if (StringUtils.hasText(traceId)) {
                MDC.put(TRACE_ID, traceId);
            } else {
                MDC.put(TRACE_ID, UUIDUtil.getUUID());
            }
            return pjp.proceed();
        } finally {
            if (pjp == beforeThreadLocal.get()) {
                MDC.remove(TRACE_ID);
                beforeThreadLocal.remove();
            }
        }
    }
}
