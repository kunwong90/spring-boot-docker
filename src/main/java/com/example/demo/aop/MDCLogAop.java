package com.example.demo.aop;

import com.example.demo.util.UUIDUtil;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
public class MDCLogAop {

    private static final String TRACE_ID = "traceId";

    @Pointcut("execution(* com.example.demo..*(..))")
    public void mdcLog() {

    }

    /*@Before("mdcLog()")
    public void before() {
        String traceId = MDC.get(TRACE_ID);
        if (StringUtils.hasText(traceId)) {
            MDC.put(TRACE_ID, traceId);
        } else {
            MDC.put(TRACE_ID, UUIDUtil.getUUID());
        }
    }

    @After("mdcLog()")
    public void after() {
        MDC.remove(TRACE_ID);
    }*/


}
