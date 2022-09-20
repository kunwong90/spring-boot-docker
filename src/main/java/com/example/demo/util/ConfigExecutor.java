

package com.example.demo.util;

import com.example.demo.executor.ExecutorFactory;
import com.example.demo.executor.NameThreadFactory;

import java.util.concurrent.*;


public final class ConfigExecutor {

    private static final Executor DUMP_EXECUTOR = ExecutorFactory.Managed
            .newSingleExecutorService("default",
                    new NameThreadFactory("com.alibaba.nacos.config.embedded.dump"));

    private static final ScheduledExecutorService TIMER_EXECUTOR = ExecutorFactory.Managed
            .newScheduledExecutorService("default", 10,
                    new NameThreadFactory("com.alibaba.nacos.config.server.timer"));

    private static final ScheduledExecutorService CAPACITY_MANAGEMENT_EXECUTOR = ExecutorFactory.Managed
            .newSingleScheduledExecutorService("default",
                    new NameThreadFactory("com.alibaba.nacos.config.CapacityManagement"));

    private static final ScheduledExecutorService ASYNC_NOTIFY_EXECUTOR = ExecutorFactory.Managed
            .newScheduledExecutorService("default", 100,
                    new NameThreadFactory("com.alibaba.nacos.config.AsyncNotifyService"));

    private static final ScheduledExecutorService CONFIG_SUB_SERVICE_EXECUTOR = ExecutorFactory.Managed
            .newScheduledExecutorService("default",
                    ThreadUtils.getSuitableThreadCount(),
                    new NameThreadFactory("com.alibaba.nacos.config.ConfigSubService"));

    private static final ScheduledExecutorService LONG_POLLING_EXECUTOR = ExecutorFactory.Managed
            .newSingleScheduledExecutorService("default",
                    new NameThreadFactory("com.alibaba.nacos.config.LongPolling"));

    private static final ScheduledExecutorService ASYNC_CONFIG_CHANGE_NOTIFY_EXECUTOR = ExecutorFactory.Managed
            .newScheduledExecutorService("default",
                    ThreadUtils.getSuitableThreadCount(),
                    new NameThreadFactory("com.alibaba.nacos.config.server.remote.ConfigChangeNotifier"));

    public static void scheduleConfigTask(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        TIMER_EXECUTOR.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    public static void executeEmbeddedDump(Runnable runnable) {
        DUMP_EXECUTOR.execute(runnable);
    }

    public static void scheduleCorrectUsageTask(Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
        CAPACITY_MANAGEMENT_EXECUTOR.scheduleWithFixedDelay(runnable, initialDelay, delay, unit);
    }

    public static void executeAsyncNotify(Runnable runnable) {
        ASYNC_NOTIFY_EXECUTOR.execute(runnable);
    }

    public static void scheduleAsyncNotify(Runnable command, long delay, TimeUnit unit) {
        ASYNC_NOTIFY_EXECUTOR.schedule(command, delay, unit);
    }

    public static int asyncNotifyQueueSize() {
        return ((ScheduledThreadPoolExecutor) ASYNC_NOTIFY_EXECUTOR).getQueue().size();
    }

    public static int asyncConfigChangeClientNotifyQueueSize() {
        return ((ScheduledThreadPoolExecutor) ASYNC_CONFIG_CHANGE_NOTIFY_EXECUTOR).getQueue().size();
    }

    public static ScheduledExecutorService getConfigSubServiceExecutor() {
        return CONFIG_SUB_SERVICE_EXECUTOR;
    }

    public static ScheduledExecutorService getClientConfigNotifierServiceExecutor() {
        return ASYNC_CONFIG_CHANGE_NOTIFY_EXECUTOR;
    }

    public static void scheduleLongPolling(Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
        LONG_POLLING_EXECUTOR.scheduleWithFixedDelay(runnable, initialDelay, delay, unit);
    }

    public static ScheduledFuture<?> scheduleLongPolling(Runnable runnable, long delay, TimeUnit unit) {
        return LONG_POLLING_EXECUTOR.schedule(runnable, delay, unit);
    }

    public static void executeLongPolling(Runnable runnable) {
        LONG_POLLING_EXECUTOR.execute(runnable);
    }
}
