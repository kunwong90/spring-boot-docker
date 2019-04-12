package com.example.demo.aop;

import com.example.demo.util.TraceIdGenerator;
import com.example.demo.util.TracerUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
public class MDCLogAop {



    private ThreadLocal<ProceedingJoinPoint> beforeThreadLocal = new ThreadLocal<>();

    @Pointcut("execution(* com.example.demo..*(..))")
    public void mdcLog() {

    }

    //@Around("mdcLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String key = TracerUtils.TRACE_ID;
        if (beforeThreadLocal.get() == null) {
            beforeThreadLocal.set(pjp);
        }
        try {
            String traceId = MDC.get(key);
            if (!StringUtils.hasText(traceId)) {
                MDC.put(key, TraceIdGenerator.generate());
            }
            return pjp.proceed();
        } finally {
            if (pjp == beforeThreadLocal.get()) {
                MDC.remove(key);
                beforeThreadLocal.remove();
            }
        }
    }
}
