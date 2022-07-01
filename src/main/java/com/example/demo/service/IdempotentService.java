package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.Date;

public interface IdempotentService {

    User printUser(User user);

    User printUser(User user, Date date);
}
