package com.example.demo.deferredresultlongpulling.controller;

import com.example.demo.deferredresultlongpulling.service.DeferredResultLongPollingService;
import com.example.demo.deferredresultlongpulling.vo.LoginResultVo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class DeferredResultLoginServletInner {

    private final DeferredResultLongPollingService longPollingService;

    public DeferredResultLoginServletInner(DeferredResultLongPollingService longPollingService) {
        this.longPollingService = longPollingService;
    }

    public DeferredResult<ResponseEntity<LoginResultVo>> doPollingLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return longPollingService.doPollingLogin(request, response).getResult();
    }


    public boolean notify(HttpServletRequest request) throws IOException {
        return longPollingService.notify(request);
    }
}
