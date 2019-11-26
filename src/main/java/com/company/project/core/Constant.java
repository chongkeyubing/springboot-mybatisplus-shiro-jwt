package com.company.project.core;

/**
 * 系统常量
 *
 * @author libaogang
 * @since 2019-10-31-22:53
 */
public class Constant {

    public static final String USER_ID = "userId";

    /**
     * request中当前用户
     */
    public static final String CURRENT_USER = "currentUser";

    /**
     * 超级管理员id
     */
    public static final long SUPER_ADMIN_ID = 1;

    /**
     * 权限类型
     */
    public enum PermissionType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        PermissionType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
