package com.pan.app.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.exception.BusinessException;
import com.pan.app.model.dto.user.UserDTO;
import com.pan.app.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Mybatis Plus允许在插入或者更新的时候
 * 自定义设定值
 * @author valarchie
 */
@Slf4j
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {
    private static final String CREATOR_ID_FIELD = "creatorId";
    private static final String CREATE_TIME_FIELD = "createTime";
    private static final String UPDATE_TIME_FIELD = "updateTime";


    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter(CREATE_TIME_FIELD)) {
            this.setFieldValByName(CREATE_TIME_FIELD, LocalDateTime.now(), metaObject);
        }
        if (metaObject.hasSetter(UPDATE_TIME_FIELD)) {
            this.setFieldValByName(UPDATE_TIME_FIELD, LocalDateTime.now(), metaObject);
        }

        Long userId = Optional.ofNullable(UserHolder.getUser())
            .map(UserDTO::getId)
            .orElseThrow(() -> new BusinessException(ResultCode.NO_AUTH));
        if (metaObject.hasSetter(CREATOR_ID_FIELD)) {
            this.strictInsertFill(metaObject, CREATOR_ID_FIELD, Long.class, userId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATE_TIME_FIELD)) {
            this.setFieldValByName(UPDATE_TIME_FIELD, LocalDateTime.now(), metaObject);
        }
    }
}
