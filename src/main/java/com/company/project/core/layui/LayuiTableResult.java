package com.company.project.core.layui;

import com.company.project.core.Result;

/**
 * layui表格数据封装
 *
 * @author libaogang
 * @since 2019-07-02 11:26
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