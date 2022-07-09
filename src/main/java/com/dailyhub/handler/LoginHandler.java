package com.dailyhub.handler;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.dailyhub.dto.UserDto;
import com.dailyhub.service.UserService;
import com.dailyhub.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;


/**
 * LoginHandler
 * @author ispy
 * @date 2022/2/2
 */
@Slf4j
@Component
public class LoginHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${server.domain}")
    private String url;

    public String checkUser(String openId,String content) {

        if (StringUtils.isBlank(openId)){
            log.info("com.dailyhub.handler.LoginHandler.checkUser.openId为空");
            return "";
        }
        UserDto userDto = userService.login(content, openId);
        log.info("com.dailyhub.handler.LoginHandler.checkUser.userDto:{}",userDto);
        //存储到redis中
        redisUtil.set("user"+content, JSONUtil.toJsonStr(userDto),10*60);

        //解决手机端登录
        String token = UUID.randomUUID().toString();
        redisUtil.set("token"+token , JSONUtil.toJsonStr(userDto),30*60);
        url = url + "/autoLogin?token="+token;
        return new String("嗨嗨嗨\n"+"<a href='"+ url + "'>手机用户点击这里完成登录</a>");

    }
}
