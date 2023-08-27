package com.example.demo.exception.handler;

import com.example.demo.entity.ResponseData;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.TimeOutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 这里就是对各个层返回的异常进行统一捕获处理
    @ExceptionHandler(value = {BusinessException.class, TimeOutException.class})
    public ResponseData<Void> bizException(Exception e) {
        LOGGER.error("业务异常记录", e);
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            return ResponseData.error(be.getCode(), be.getMessage());
        }
        if (e instanceof TimeOutException) {
            TimeOutException te = (TimeOutException) e;
            return ResponseData.error(te.getCode(), te.getMessage());
        }
        return ResponseData.error(0, "业务异常");
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseData<Void> exception(Exception e) {
        LOGGER.error("系统异常记录", e);
        return ResponseData.error(0, "系统异常");
    }


    // 将前端传入的字符串时间格式转换为LocalDate时间
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
//将前端传入的字符串格式时间数据转为LocalDate格式的数据
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });
//将前端传入的字符串格式时间数据转为LocalDateTime格式的数据
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        });
//将前端传入的字符串格式时间数据转为LocalTim格式的数据
        binder.registerCustomEditor(LocalTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        });
    }

}