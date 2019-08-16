package com.company.project.core;

/**
 * 业务异常统一抛出该类，由GlobalExceptionHanlder统一处理
 *
 * @author libaogang
 * @since 2019-06-06 23:40
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}