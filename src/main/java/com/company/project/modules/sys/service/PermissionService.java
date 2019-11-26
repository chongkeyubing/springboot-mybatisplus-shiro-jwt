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
     * 根据用户id查询权限列表
     *
     * @author libaogang
     * @param userId 用户id
     * @return java.util.List<com.company.project.modules.sys.entity.PermissionEntity>
     * @date 2019-11-26 17:00:55
     */
    List<PermissionEntity> listPermissionsByUserId(Long userId);


    /**
     * 根据角色id列表查询权限
     *
     * @param roleIds 角色id列表
     * @return java.util.List<com.company.project.modules.sys.entity.PermissionEntity>
     * @author libaogang
     * @date 2019-11-26 14:25:32
     */
    List<PermissionEntity> listPermissionsByRoleIds(List<Long> roleIds);

    /**
     * 根据用户id查询导航菜单树
     *
     * @param userId 用户id
     * @return java.util.List<com.company.project.modules.sys.dto.MenuDTO>
     * @author libaogang
     * @date 2019-11-26 15:13:07
     */
    List<MenuDTO> getUserMenuTree(Long userId);

    /**
     * 获取权限树
     *
     * @author libaogang
     * @return java.util.List<com.company.project.modules.sys.entity.PermissionEntity>
     * @date 2019-11-26 16:09:08
     */
    List<PermissionEntity> getPermissionTree();

    /**
     * 根据权限列表构建权限树
     *
     * @author libaogang
     * @param permissions 权限列表
     * @return 权限树
     * @date 2019-11-26 16:10:10
     */
    List<PermissionEntity> constractPermissionTree(List<PermissionEntity> permissions);
}
