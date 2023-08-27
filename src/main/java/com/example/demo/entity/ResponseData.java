package com.example.demo.entity;

public class ResponseData<T> {
    private boolean success;

    private String msg;

    private int code;

    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResponseData<Void> error(int code, String msg) {
        ResponseData<Void> responseData = new ResponseData<>();
        responseData.setSuccess(false);
        responseData.setCode(code);
        responseData.setMsg(msg);
        return responseData;
    }

    public static ResponseData<Void> success(int code, String msg) {
        ResponseData<Void> responseData = new ResponseData<>();
        responseData.setSuccess(true);
        responseData.setCode(code);
        responseData.setMsg(msg);
        return responseData;
    }
}
