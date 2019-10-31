package com.company.project.core.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.company.project.core.Constant;
import com.company.project.util.WebContextUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * mybatisplus字段自动填充处理器,插入和更新时自动填充相关字段
 *
 * @author libaogang
 * @since 2019-10-31-22:00
 */
@Component
public class MyMeTaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
//        if(metaObject.hasSetter("createTime")){
//            setInsertFieldValByName("createTime",new Date() ,metaObject);
//        }
        if (metaObject.hasSetter("createUserId")) {
            Long userId = (Long) WebContextUtil.getRequest().getAttribute(Constant.USER_ID);
            setInsertFieldValByName("createUserId", userId, metaObject);
        }
        if (metaObject.hasSetter("createUserName")) {
            String realname = (String) WebContextUtil.getRequest().getAttribute(Constant.REALNAME);
            setInsertFieldValByName("createUserName", realname, metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        if (metaObject.hasSetter("updateTime")) {
//            setUpdateFieldValByName("updateTime", new Date(), metaObject);
//        }
        if (metaObject.hasSetter("updateUserId")) {
            Long userId = (Long) WebContextUtil.getRequest().getAttribute(Constant.USER_ID);
            setUpdateFieldValByName("updateUserId", userId, metaObject);
        }
    }
}
