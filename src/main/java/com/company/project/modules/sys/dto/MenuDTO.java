package com.company.project.modules.sys.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单实体
 *
 * @author libaogang
 * @since 2019-11-26 上午 11:11
 */
@Data
@Accessors(chain = true)
public class MenuDTO implements Serializable {
    private Long menuId;

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
     * 菜单URL
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 子菜单列表
     */
    List<MenuDTO> subMenus;
}