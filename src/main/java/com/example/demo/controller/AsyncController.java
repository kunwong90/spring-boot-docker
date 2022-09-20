package com.example.demo.controller;

import com.example.demo.notify.NotifyCenter;
import com.example.demo.notify.event.LocalDataChangeEvent;
import com.example.demo.service.impl.LongPullingServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
public class AsyncController {

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

    @GetMapping(value = "/async")
    public void async(HttpServletRequest request, HttpServletResponse response) {
        LongPullingServiceImpl longPullingService = new LongPullingServiceImpl();
        longPullingService.doPolling(request);
    }

    @GetMapping(value = "/sync")
    public void sync(HttpServletRequest request, HttpServletResponse response) {
        try {
            TimeUnit.SECONDS.sleep(3);
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-cache,no-store");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("sync success");
        } catch (Exception e) {

        }


    }

    @GetMapping(value = "/notify")
    public String notify1() {
        boolean success = NotifyCenter.publishEvent(new LocalDataChangeEvent("test"));
        return "{\"success\":" + success + "}";
    }
}
