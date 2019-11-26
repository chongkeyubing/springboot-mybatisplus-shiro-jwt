package com.company.project.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author libaogang
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
public class PermissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "permission_id", type = IdType.AUTO)
    private Long permissionId;

    /**
     * 菜单名称
     */
    private String permissionName;

    /**
     * 类型 0：目录   1：菜单   2：操作
     */
    private Integer type;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 授权标志(如user:add)
     */
    private String permessionFlag;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;


}
