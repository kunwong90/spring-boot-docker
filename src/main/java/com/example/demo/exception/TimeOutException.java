package com.example.demo.exception;

public class TimeOutException extends RuntimeException {
    private int code;

    private String msg;

    public TimeOutException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
