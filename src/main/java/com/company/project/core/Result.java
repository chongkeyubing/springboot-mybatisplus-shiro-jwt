package com.company.project.core;

/**
 * ajax响应结果
 *
 * @author libaogang
 * @since 2019-06-06 23:40
 */
public class Result<T> {
    public static final int SUCCESS = 0;

    /**
     * 业务异常
     */
    public static final int BUSINESS_EXCEPTION = 1;

    /**
     * token异常
     */
    public static final int TOKEN_EXCEPTION = 2;

    /**
     * 无权限
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 服务器内部错误
     */
    public static final int INNER_ERROR = 500;

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
