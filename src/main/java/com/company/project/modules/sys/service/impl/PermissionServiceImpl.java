package com.company.project.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.core.Constant;
import com.company.project.modules.sys.dto.MenuDTO;
import com.company.project.modules.sys.entity.PermissionEntity;
import com.company.project.modules.sys.entity.RoleEntity;
import com.company.project.modules.sys.mapper.PermissionMapper;
import com.company.project.modules.sys.service.PermissionService;
import com.company.project.modules.sys.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author libaogang
 * @since 2019-11-26
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {

    @Resource
    private RoleService roleService;

    /**
     * 根据用户id查询操作权限
     *
     * @param userId 用户id
     * @return java.util.List<com.company.project.modules.sys.entity.PermissionEntity>
     * @author libaogang
     * @date 2019-11-26 17:00:55
     */
    @Override
    public List<PermissionEntity> listPermissionsByUserId(Long userId) {
        List<RoleEntity> roleEntities = roleService.listRolesByUserId(userId);
        List<Long> roleIds = new ArrayList<>(roleEntities.size());
        for (RoleEntity roleEntity : roleEntities) {
            roleIds.add(roleEntity.getRoleId());
        }
        return this.listPermissionsByRoleIds(roleIds);
    }

    /**
     * 根据角色id列表查询操作权限
     *
     * @param roleIds 角色id列表
     * @return java.util.List<com.company.project.modules.sys.entity.PermissionEntity>
     * @author libaogang
     * @date 2019-11-26 14:25:32
     */
    @Override
    public List<PermissionEntity> listPermissionsByRoleIds(List<Long> roleIds) {
        return baseMapper.listPermissionsByRoleIds(roleIds);
    }

    /**
     * 根据用户id查询菜单列表
     *
     * @param userId 用户id
     * @return java.util.List<com.company.project.modules.sys.entity.PermissionEntity>
     * @author libaogang
     * @since 2019-11-27 01:07:56
     */
    @Override
    public List<PermissionEntity> listMenusByUserId(Long userId) {
        List<RoleEntity> roleEntities = roleService.listRolesByUserId(userId);
        List<Long> roleIds = new LinkedList<>();
        for (RoleEntity roleEntity : roleEntities) {
            roleIds.add(roleEntity.getRoleId());
        }
        return baseMapper.listMenusByRoleIds(roleIds);
    }

    /**
     * 根据用户id查询导航菜单树
     *
     * @param userId 用户id
     * @return java.util.List<com.company.project.modules.sys.dto.MenuDTO>
     * @author libaogang
     * @date 2019-11-26 15:13:07
     */
    @Override
    public List<MenuDTO> getUserMenuTree(Long userId) {
        if (userId == Constant.SUPER_ADMIN_ID) {
            // 所有菜单
            List<PermissionEntity> permissionEntities = this.lambdaQuery()
                    .eq(PermissionEntity::getType, Constant.PermissionType.CATALOG).or()
                    .eq(PermissionEntity::getType, Constant.PermissionType.MENU)
                    .list();
            return constructMenuTree(permissionEntities);
        } else {
            List<PermissionEntity> permissionEntities = listMenusByUserId(userId);
            return constructMenuTree(permissionEntities);
        }

    }

    /**
     * 根据菜单列表构建菜单树
     *
     * @param allMenus 所有菜单列表
     * @return java.util.List<com.company.project.modules.sys.dto.MenuDTO>
     * @author libaogang
     * @date 2019-11-26 16:26:21
     */
    private List<MenuDTO> constructMenuTree(List<PermissionEntity> allMenus) {
        // 获取一级目录
        List<PermissionEntity> rootMenus = this.getSubMenusByParentId(0L, allMenus);

        List<MenuDTO> menuTree = constructMenuDTOList(rootMenus);

        recursiveConstructMenuTree(menuTree, allMenus);

        return menuTree;
    }

    /**
     * 递归构建菜单树
     */
    private void recursiveConstructMenuTree(List<MenuDTO> menuList, List<PermissionEntity> allMenus) {
        for (MenuDTO menuDTO : menuList) {
            if (menuDTO.getType() == Constant.PermissionType.CATALOG.getValue()) {
                List<PermissionEntity> subMenus = this.getSubMenusByParentId(menuDTO.getMenuId(), allMenus);
                List<MenuDTO> menuDTOS = constructMenuDTOList(subMenus);
                menuDTO.setSubMenus(menuDTOS);

                recursiveConstructMenuTree(menuDTOS, allMenus);
            }
        }
    }

    /**
     * 根据父id获取子菜单
     */
    private List<PermissionEntity> getSubMenusByParentId(long parentId, List<PermissionEntity> allMenus) {
        List<PermissionEntity> subMenus = new ArrayList<>();
        for (PermissionEntity permissionEntity : allMenus) {
            if (permissionEntity.getParentId() == parentId) {
                subMenus.add(permissionEntity);
            }
        }
        return subMenus;
    }

    private List<MenuDTO> constructMenuDTOList(List<PermissionEntity> permissionEntityList) {
        List<MenuDTO> menuDTOS = new LinkedList<>();
        for (PermissionEntity permissionEntity : permissionEntityList) {
            MenuDTO menuDTO = new MenuDTO()
                    .setMenuId(permissionEntity.getPermissionId())
                    .setIcon(permissionEntity.getIcon())
                    .setOrderNum(permissionEntity.getOrderNum())
                    .setParentId(permissionEntity.getParentId())
                    .setType(permissionEntity.getType())
                    .setUrl(permissionEntity.getUrl())
                    .setName(permissionEntity.getPermissionName());
            menuDTOS.add(menuDTO);
        }
        return menuDTOS;
    }

}
