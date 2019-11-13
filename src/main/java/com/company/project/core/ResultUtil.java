package com.company.project.core;

/**
 * 响应结果生成工具
 *
 * @author libaogang
 * @since 2019-06-06 23:40
 */
public class ResultUtil {

    public static Result success() {
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMsg(Result.SUCCESS_MSG);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(Result.SUCCESS);
        result.setMsg(Result.SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    public static Result fail(int code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(message);
        return result;
    }

}
