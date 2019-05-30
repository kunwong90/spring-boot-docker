package com.example.demo.thread;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class TracerThreadPoolExecutor extends ThreadPoolExecutor {

    final private boolean useFixedContext;
    final private Map<String, String> fixedContext;

    /**
     * Pool where task threads take MDC from the submitting thread.
     */
    public static TracerThreadPoolExecutor newWithInheritedMdc(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                                               TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return new TracerThreadPoolExecutor(null, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * Pool where task threads take fixed MDC from the thread that creates the pool.
     */
    public static TracerThreadPoolExecutor newWithCurrentMdc(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                                             TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return new TracerThreadPoolExecutor(MDC.getCopyOfContextMap(), corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue);
    }

    /**
     * Pool where task threads always have a specified, fixed MDC.
     */
    public static TracerThreadPoolExecutor newWithFixedMdc(Map<String, String> fixedContext, int corePoolSize,
                                                           int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                                           BlockingQueue<Runnable> workQueue) {
        return new TracerThreadPoolExecutor(fixedContext, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    private TracerThreadPoolExecutor(Map<String, String> fixedContext, int corePoolSize, int maximumPoolSize,
                                     long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.fixedContext = fixedContext;
        this.useFixedContext = (fixedContext != null);
    }


    private Map<String, String> getContextForTask() {
        return useFixedContext ? fixedContext : MDC.getCopyOfContextMap();
    }

    /**
     * All executions will have MDC injected. {@code ThreadPoolExecutor}'s submission methods ({@code submit()} etc.)
     * all delegate to this.
     */
    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, getContextForTask()));
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            Map<String, String> previous = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                runnable.run();
            } finally {
                if (previous == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(previous);
                }
            }
        };
    }
}
