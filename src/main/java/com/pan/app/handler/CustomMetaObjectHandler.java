package com.pan.app.handler;

import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.pan.app.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mybatis Plus允许在插入或者更新的时候
 * 自定义设定值
 * @author valarchie
 */
@Slf4j
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {
    private static final Long DEFAULT_USER_ID = 0L;
    private static final String CREATOR_ID_FIELD = "creatorId";
    private static final String CREATE_TIME_FIELD = "createTime";
    private static final String UPDATER_ID_FIELD = "updaterId";

    private static final String UPDATE_TIME_FIELD = "updateTime";


    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = getUserId();
        if (metaObject.hasSetter(CREATOR_ID_FIELD)) {
            if (metaObject.getValue(CREATOR_ID_FIELD) == null) {
                this.strictInsertFill(metaObject, CREATOR_ID_FIELD, Long.class, userId);
            }
        }

        if (metaObject.hasSetter(UPDATER_ID_FIELD)) {
            if (metaObject.getValue(UPDATER_ID_FIELD) == null) {
                this.strictInsertFill(metaObject, UPDATER_ID_FIELD, Long.class, userId);
            }
        }

        if (metaObject.hasSetter(CREATE_TIME_FIELD)) {
            this.setFieldValByName(CREATE_TIME_FIELD, LocalDateTime.now(), metaObject);
        }
        if (metaObject.hasSetter(UPDATE_TIME_FIELD)) {
            this.setFieldValByName(UPDATE_TIME_FIELD, LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = getUserId();

        if (metaObject.hasSetter(UPDATER_ID_FIELD)) {
            if (metaObject.getValue(UPDATER_ID_FIELD) == null) {
                this.strictInsertFill(metaObject, UPDATER_ID_FIELD, Long.class, userId);
            }
        }

        if (metaObject.hasSetter(UPDATE_TIME_FIELD)) {
            this.setFieldValByName(UPDATE_TIME_FIELD, LocalDateTime.now(), metaObject);
        }
    }

    private Long getUserId() {
        if (SpringMVCUtil.isWeb() && StpUtil.isLogin()) {
            return AuthUtils.getLoginUserId();
        }

        return DEFAULT_USER_ID;
    }
}
