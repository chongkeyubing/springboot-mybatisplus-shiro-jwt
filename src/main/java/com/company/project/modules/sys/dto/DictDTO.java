package com.company.project.modules.sys.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

/**
 * 字典DTO
 *
 * @author libaogang
 * @since 2019-11-28 下午 3:31
 */
@Data
@Accessors(chain = true)
public class DictDTO {

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典项
     */
    private List<DictItemDTO> dictItems = new LinkedList<>();

}