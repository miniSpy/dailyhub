package com.dailyhub.common.interceptor;

import com.dailyhub.annotation.Login;
import com.dailyhub.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  登錄拦截器
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("handler+++++:{}",handler);
        //判断是否是springmvc请求
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        UserDto userInfo = (UserDto)request.getSession().getAttribute("userInfo");

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Login methodAnnotation = handlerMethod.getMethodAnnotation(Login.class);
        if(methodAnnotation == null){
            log.info("方法上没有@login注解:{}",handlerMethod.getMethod());
            return true;
        }

         userInfo = (UserDto)request.getSession().getAttribute("userInfo");
        //是否登录
        if (ObjectUtils.isEmpty(userInfo)){
            log.info("com.dailyhub.common.interceptor.LoginInterceptor.preHandle.userInfo is null");
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }

        log.info("user has been login");
        return true;
    }
}
