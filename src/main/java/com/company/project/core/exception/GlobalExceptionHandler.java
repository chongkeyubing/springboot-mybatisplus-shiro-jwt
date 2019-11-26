package com.company.project.core.exception;

import com.company.project.core.Result;
import com.company.project.core.ResultUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理
 *
 * @author libaogang
 * @since 2019-08-16 17:20
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //业务异常
    @ExceptionHandler(value = BusinessException.class)
    public Result serviceException(HttpServletRequest request, Exception e, HttpServletResponse response) {
        LOGGER.error("业务异常：{}", e.getMessage());
        return ResultUtil.fail(Result.BUSINESS_EXCEPTION, e.getMessage());
    }

    //shiro
    @ExceptionHandler(value = UnauthorizedException.class)
    public Result unauthorizedException(HttpServletRequest request, Exception e, HttpServletResponse response) {
        return ResultUtil.fail(Result.UNAUTHORIZED,"无权访问");
    }

    //内部错误
    @ExceptionHandler(value = Exception.class)
    public Result exception(HttpServletRequest request, Exception e, HttpServletResponse response) {
        LOGGER.error("接口 [" + request.getRequestURI() + "] 内部错误：" + e.getMessage(), e);
        return ResultUtil.fail(Result.INNER_ERROR, "接口 [" + request.getRequestURI() + "] 内部错误：" + e.getMessage());
    }

}