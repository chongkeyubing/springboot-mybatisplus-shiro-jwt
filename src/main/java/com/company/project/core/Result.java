package com.company.project.core;

/**
 * @Author: libaogang
 * @Date: 2019-06-06 23:40
 * @Description ajax响应结果封装
 */
public class Result<T> {
    private int code;
    private boolean success;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
