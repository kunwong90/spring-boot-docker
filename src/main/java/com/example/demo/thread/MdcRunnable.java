package com.example.demo.thread;

import org.slf4j.MDC;

import java.util.Map;

public abstract class MdcRunnable implements Runnable {

    private Map<String, String> context;

    public MdcRunnable() {
        this.context = MDC.getCopyOfContextMap();
    }

    @Override
    public void run() {
        Map<String, String> previous = MDC.getCopyOfContextMap();
        if (context == null) {
            MDC.clear();
        } else {
            MDC.setContextMap(context);
        }
        try {
            runWithMDC();
        } finally {
            if (previous == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(previous);
            }
        }
    }

    public abstract void runWithMDC();
}
