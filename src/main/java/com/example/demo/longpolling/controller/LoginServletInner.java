package com.example.demo.longpolling.controller;

import com.example.demo.longpolling.service.LongPollingService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class LoginServletInner {

    private final LongPollingService longPollingService;

    public LoginServletInner(LongPollingService longPollingService) {
        this.longPollingService = longPollingService;
    }

    public void doPollingLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        longPollingService.doPollingLogin(request, response);
    }


    public boolean notify(HttpServletRequest request) throws IOException {
        return longPollingService.notify(request);
    }
}
