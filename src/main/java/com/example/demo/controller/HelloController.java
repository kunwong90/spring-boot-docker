package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);


    @Value(value = "${envName}")
    private String envName;

    @GetMapping(value = "/envName")
    private String envName() {
        return envName;
    }


    @GetMapping(value = "/hello")
    public String hello() {
        LOGGER.info("my first spring-boot-docker application");
        return "my first spring-boot-docker application";
    }
}
