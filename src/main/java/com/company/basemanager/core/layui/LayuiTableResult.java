package com.company.basemanager.core.layui;

import com.company.basemanager.core.Result;

/**
 * @Author: libaogang
 * @Date: 2019-07-02 11:26
 * @Description layui表格数据封装
 */
public class LayuiTableResult<T> extends Result<T> {
    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}