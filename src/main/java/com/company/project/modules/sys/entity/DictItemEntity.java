package com.company.project.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典项表
 * </p>
 *
 * @author libaogang
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict_item")
public class DictItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "item_id", type = IdType.AUTO)
    private Long itemId;

    /**
     * 字典编码
     */
    private String dictCode;

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

    /**
     * 状态：0正常1停用
     */
    private Boolean status;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUserId;

    /**
     * 更新时间
     */
    private Date updateTime;


}
