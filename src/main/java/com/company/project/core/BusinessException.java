package com.company.project.core;

/**
 * 业务异常
 *
 * @author libaogang
 * @since 2019-06-06 23:40
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}