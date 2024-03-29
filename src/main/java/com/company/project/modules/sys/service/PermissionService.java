package com.company.project.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.modules.sys.dto.MenuDTO;
import com.company.project.modules.sys.entity.PermissionEntity;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author libaogang
 * @since 2019-11-26
 */
public interface PermissionService extends IService<PermissionEntity> {
    /**
     * 根据用户id查询操作权限列表
     *
     * @author libaogang
     * @param userId 用户id
     * @return java.util.List<com.company.project.modules.sys.entity.PermissionEntity>
     * @date 2019-11-26 17:00:55
     */
    List<PermissionEntity> listPermissionsByUserId(Long userId);

    /**
     * 根据角色id列表查询操作权限列表
     *
     * @param roleIds 角色id列表
     * @return java.util.List<com.company.project.modules.sys.entity.PermissionEntity>
     * @author libaogang
     * @date 2019-11-26 14:25:32
     */
    List<PermissionEntity> listPermissionsByRoleIds(List<Long> roleIds);

    /**
     * 根据用户id查询菜单列表
     *
     * @author libaogang
     * @since 2019-11-27 01:07:56
     * @param userId 用户id
     * @return java.util.List<com.company.project.modules.sys.entity.PermissionEntity>
     */
    List<PermissionEntity> listMenusByUserId(Long userId);

    /**
     * 根据用户id查询菜单树
     *
     * @param userId 用户id
     * @return java.util.List<com.company.project.modules.sys.dto.MenuDTO>
     * @author libaogang
     * @date 2019-11-26 15:13:07
     */
    List<MenuDTO> getUserMenuTree(Long userId);

}
