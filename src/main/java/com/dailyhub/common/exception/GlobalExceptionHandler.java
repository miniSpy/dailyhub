package com.dailyhub.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重寫方法實現全局異常捕獲
 * @date 2021/2/2
 * @author ispy
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    protected static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if(ex instanceof IllegalArgumentException)
            logger.info("IllegalArgumentException:{}",ex.getMessage());
        //如果是ajax请求
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            try {
                response.getWriter().write("请求出现错误");
                return new ModelAndView();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                logger.info("resolveException.ajax.exception:{}",ex.getMessage());
            }
        }

        //非ajax出现的异常
        logger.info("not ajax exception:{}",ex.getMessage());
        request.setAttribute("error","出现了错误");
        return new ModelAndView("error");
    }
}
