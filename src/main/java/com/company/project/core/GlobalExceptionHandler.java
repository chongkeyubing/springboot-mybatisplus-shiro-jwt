package com.company.project.core;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理
 *
 * @author libaogang
 * @since 2019-08-16 17:20
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result ExceptionHandler(HttpServletRequest request, Exception e, HttpServletResponse response) {
        if (e instanceof ServiceException) {
            return ResultUtil.fail(e.getMessage());
        } else if (e instanceof UnauthorizedException) {
            return ResultUtil.fail("无权访问");
        } else {
            //其他异常
            LOGGER.error("接口 [" + request.getRequestURI() + "] 内部错误：" + e.getMessage(), e);
            return ResultUtil.fail("接口 [" + request.getRequestURI() + "] 内部错误");
        }
    }

}