package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.IdempotentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping
public class IdempotentController {

    @Resource
    private IdempotentService idempotentService;

    @PostMapping(value = "/printUser")
    public User printUser(@RequestBody User user) {
        try {
            return idempotentService.printUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/printUser1")
    public User printUser1(@RequestBody User user) {
        try {
            return idempotentService.printUser(user, new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
