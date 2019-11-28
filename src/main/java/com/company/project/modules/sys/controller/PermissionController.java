package com.company.project.modules.sys.controller;

import com.company.project.core.Result;
import com.company.project.core.ResultUtil;
import com.company.project.modules.sys.dto.MenuDTO;
import com.company.project.modules.sys.service.PermissionService;
import com.company.project.util.CurrentUserUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author libaogang
 * @since 2019-11-26
 */
@RestController
@RequestMapping("/sys/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 查询用户菜单树
     *
     * @return 菜单树
     * @author libaogang
     * @date 2019-11-27 09:13:28
     */
    @RequestMapping("/getUserMenuTree")
    @RequiresPermissions("sys:user:add")
    public Result getUserMenuTree() {
        List<MenuDTO> userMenuTree = permissionService.getUserMenuTree(CurrentUserUtil.getCurrentUserId());
        return ResultUtil.success(userMenuTree);
    }

}
