package com.pan.app.model.converter.user;

import com.pan.app.model.entity.User;
import com.pan.app.model.req.user.UserAddReq;
import com.pan.app.model.req.user.UserQueryReq;
import com.pan.app.model.req.user.UserUpdateReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-02-01 15:31
 **/
@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    User toUser(UserAddReq userAddReq);
    
    User toUser(UserQueryReq userQueryReq);

    User toUser(UserUpdateReq userUpdateReq);
}
