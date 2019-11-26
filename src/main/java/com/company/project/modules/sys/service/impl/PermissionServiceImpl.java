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
     * 根据用户id查询菜单
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
     * 根据角色id列表查询权限
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
//            this.lambdaQuery()
//                    .eq(PermissionEntity::getType, Constant.PermissionType.CATALOG)
//                    .or()
//                    .eq(PermissionEntity::getType, Constant.PermissionType.MENU)
//                    .list();

            List<PermissionEntity> menus = this.lambdaQuery()
                    .or(i -> i.eq(PermissionEntity::getType, Constant.PermissionType.CATALOG)
                            .eq(PermissionEntity::getType, Constant.PermissionType.MENU)
                    ).list();
            return constractMenuTree(menus);
        } else {

            return constractMenuTree(this.list());
        }

    }

    /**
     * 根据菜单列表构建菜单树
     *
     * @param menus 菜单列表
     * @return java.util.List<com.company.project.modules.sys.dto.MenuDTO>
     * @author libaogang
     * @date 2019-11-26 16:26:21
     */
    private List<MenuDTO> constractMenuTree(List<PermissionEntity> menus) {

        return null;
    }





    /**
     * 获取权限树
     *
     * @return java.util.List<com.company.project.modules.sys.entity.PermissionEntity>
     * @author libaogang
     * @date 2019-11-26 16:09:08
     */
    @Override
    public List<PermissionEntity> getPermissionTree() {

        return null;
    }

    /**
     * 根据权限列表构建权限树
     *
     * @param permissions 权限列表
     * @return 权限树
     * @author libaogang
     * @date 2019-11-26 16:10:10
     */
    @Override
    public List<PermissionEntity> constractPermissionTree(List<PermissionEntity> permissions) {

        return null;
    }




}
