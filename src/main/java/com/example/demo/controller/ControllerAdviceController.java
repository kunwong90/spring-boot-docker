package com.example.demo.controller;

import com.example.demo.entity.ResponseData;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.TimeOutException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ControllerAdviceController {

    @GetMapping(value = "/exception")
    public ResponseData<Void> exception() {
        int i = 1 / 0;
        return ResponseData.success(1, "成功");
    }

    @GetMapping(value = "/bizExcetion")
    public ResponseData<Void> bizExcetion() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new BusinessException(0, "抛出BusinessException");
        }
        return ResponseData.success(1, "成功");
    }

    @GetMapping(value = "/bizExcetion1")
    public ResponseData<Void> bizExcetion1() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new TimeOutException(0, "抛出TimeOutException");
        }
        return ResponseData.success(1, "成功");
    }

    @GetMapping(value = "/time")
    public ResponseData<Void> time(@RequestParam LocalDate date) {
        System.out.println(date);

        return ResponseData.success(1, "成功");
    }
}
