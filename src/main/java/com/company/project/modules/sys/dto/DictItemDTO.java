package com.company.project.modules.sys.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 字典项DTO
 *
 * @author libaogang
 * @since 2019-11-28 下午 3:32
 */
@Data
@Accessors(chain = true)
public class DictItemDTO {

    /**
     * 字典文本
     */
    private String text;

    /**
     * 字典值
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;
}