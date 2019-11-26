package com.company.project.core.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.company.project.modules.sys.entity.UserEntity;
import com.company.project.util.CurrentUserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * mybatisplus字段自动填充处理器,插入和更新时自动填充相关字段
 *
 * @author libaogang
 * @since 2019-10-31-22:00
 */
@Component
public class CustomMeTaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_USER_ID = "createUserId";

    private static final String CREATE_USER_NAME = "createUserName";

    private static final String UPDATE_USER_ID = "updateUserId";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter(CREATE_USER_ID)) {
            UserEntity user = CurrentUserUtil.getCurrentUser();
            setInsertFieldValByName(CREATE_USER_ID, user.getUserId(), metaObject);
        }
        if (metaObject.hasSetter(CREATE_USER_NAME)) {
            UserEntity user = CurrentUserUtil.getCurrentUser();
            setInsertFieldValByName(CREATE_USER_NAME, user.getRealname(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATE_USER_ID)) {
            UserEntity user = CurrentUserUtil.getCurrentUser();
            setUpdateFieldValByName(UPDATE_USER_ID, user.getUserId(), metaObject);
        }
    }
}
