package com.company.project.util;

import com.company.project.core.Constant;
import com.company.project.modules.sys.entity.UserEntity;

/**
 * 用于获取当前用户
 *
 * @author libaogang
 * @since 2019-11-26 下午 12:35
 */
public class CurrentUserUtil {

    /**
     * 获取当前用户id
     *
     * @return long
     * @author libaogang
     * @date 2019-11-26 12:38:22
     */
    public static long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    /**
     * 获取当前用户
     *
     * @return com.company.project.modules.sys.entity.UserEntity
     * @author libaogang
     * @date 2019-11-26 12:39:53
     */
    public static UserEntity getCurrentUser() {
        return (UserEntity) WebContextUtil.getRequest().getAttribute(Constant.CURRENT_USER);
    }


}