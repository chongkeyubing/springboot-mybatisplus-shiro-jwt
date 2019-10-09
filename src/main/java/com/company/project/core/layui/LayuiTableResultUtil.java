package com.company.project.core.layui;

import com.company.project.core.Result;
import com.company.project.core.ResultUtil;

/**
 * layui表格结果生成工具
 *
 * @author libaogang
 * @since 2019-07-02 11:28
 */
public class LayuiTableResultUtil extends ResultUtil {

    public static <T> Result<T> success(T data, long count) {
        LayuiTableResult<T> result = new LayuiTableResult<>();
        result.setCount(count);
        result.setCode(Result.SUCCESS);
        result.setMsg("success");
        result.setData(data);
        return result;
    }
}