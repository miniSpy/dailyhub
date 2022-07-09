package com.dailyhub.service;

import com.dailyhub.dto.UserDto;
import com.dailyhub.entity.User;


public interface UserService  {

    /**
     * 通过验证码与openId进行用户校验
     * @param code
     * @param openId
     * @return
     */
    UserDto login(String code,String openId);

}
