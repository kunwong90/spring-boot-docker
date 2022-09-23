package com.example.demo.deferredresultlongpulling.controller;

import com.example.demo.deferredresultlongpulling.vo.LoginResultVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/login")
public class DeferredResultLoginController {

    private final DeferredResultLoginServletInner inner;

    public DeferredResultLoginController(DeferredResultLoginServletInner inner) {
        this.inner = inner;
    }

    @PostMapping("/listener1")
    public DeferredResult<ResponseEntity<LoginResultVo>> listener(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return inner.doPollingLogin(request, response);
    }


    @PostMapping(value = "/notify1")
    public String notify(HttpServletRequest request) throws IOException {
        boolean success = inner.notify(request);
        return "{\"success\":" + success + "}";
    }
}
