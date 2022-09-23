package com.example.demo.longpolling.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    private final LoginServletInner inner;

    public LoginController(LoginServletInner inner) {
        this.inner = inner;
    }

    @PostMapping("/listener")
    public void listener(HttpServletRequest request, HttpServletResponse response) throws IOException {
        inner.doPollingLogin(request, response);
    }


    @PostMapping(value = "/notify")
    public String notify(HttpServletRequest request) throws IOException {
        boolean success = inner.notify(request);
        return "{\"success\":" + success + "}";
    }
}
