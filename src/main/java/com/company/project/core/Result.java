package com.company.project.core;

import lombok.Data;

/**
 * ajax响应结果
 *
 * @author libaogang
 * @since 2019-06-06 23:40
 */
@Data
public class Result<T> {
    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 默认响应消息
     */
    public static final String SUCCESS_MSG = "success";

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
}
