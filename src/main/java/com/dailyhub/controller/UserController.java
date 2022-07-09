package com.dailyhub.controller;

import cn.hutool.json.JSONUtil;
import com.dailyhub.common.ApiResult;
import com.dailyhub.dto.UserDto;
import com.dailyhub.entity.User;
import com.dailyhub.repository.UserRepository;
import com.dailyhub.util.LoginUtil;
import com.dailyhub.util.RedisUtil;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * UserController
 * @date 2020/1/2
 * @author ispy
 */
@Controller
public class UserController extends BaseController{

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private LoginUtil loginUtil;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpMessageRouter wxMpMessageRouter;

    @Autowired
    private RedisUtil redisUtil;

    protected static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/test")
    public String test(HttpServletRequest request){
        User user = userRepository.getReferenceById((long) 2);
        request.setAttribute("username",user.getUsername());
        return "test";
    }

    @GetMapping("/login")
    public String login(){
        //获取code与ticket
        List<String> codeAndTicket = loginUtil.getCodeAndTicket();
        logger.info("com.dailyhub.controller.UserController.login.codeAndTicket:{},{}",codeAndTicket.get(0),codeAndTicket.get(1));
        //返回前端code
        request.setAttribute("code",codeAndTicket.get(0));
        request.setAttribute("ticket",codeAndTicket.get(1));
        return "login";
    }

    //局部更新 异步ajax

    /**
     *
     * @param code 验证码
     * @param ticket ticket帮助校验验证码
     * @return
     */
    @ResponseBody
    @GetMapping("/login/check")
    public ApiResult checkLogin(String code,String ticket){
        //登录校驗逻辑
        if(StringUtils.isBlank(code) ||StringUtils.isBlank(ticket))
        {
            logger.info("com.dailyhub.controller.UserController.checkLogin前端传递的参数为空");
            return ApiResult.failResult("验证码或ticket为空");
        }
//        Assert.notNull(code);
        String userInfo = redisUtil.get("user" + code).toString();
        UserDto userDto = JSONUtil.toBean(userInfo, UserDto.class);
        logger.info("com.dailyhub.controller.UserController.checkLogin.userDto:{}",userDto);
        //比对redis与前端传递的ticket是否一致
        String ticketInRedis = (String)redisUtil.get(code);
        if (ObjectUtils.isEmpty(userDto) && ticket.equals(ticketInRedis) ){
            return ApiResult.failResult("未获取到用户信息");
        }
        request.getSession().setAttribute("userInfo",userDto);
        return ApiResult.successResult(200,"登陆成功",null);
    }

    @RequestMapping("/autoLogin")
    public String autoLogin(String token){
        if(StringUtils.isBlank(token)){
            logger.info("com.dailyhub.controller.UserController.autoLogin.token为空");
            return "error";
        }
        String userInfo = redisUtil.get("token" + token).toString();
        UserDto userDto = JSONUtil.toBean(userInfo, UserDto.class);
        if(ObjectUtils.isEmpty(userDto)){
            return "error";
        }
        else{
            request.getSession().setAttribute("userInfo",userDto);
            return "redirect:/" ;
        }
    }

    @ResponseBody
    @RequestMapping("/wx/back")
    public String wxBack(String signature, String timestamp,String nonce,String echostr) throws IOException {

//        接入生效
        if(StringUtils.isNotBlank(echostr)){
            logger.info("配置称为开发者成功:{}",echostr);
            return echostr;
        }

//        校驗是否是微信服务器发送的消息是否合法
        if(!wxMpService.checkSignature(timestamp,nonce,signature)){
            logger.info("签名不合法");
            return "";
        }
        //获取信息
        WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(wxMpXmlMessage);
        logger.info("com.dailyhub.controller.UserController.wxBack.msg:{}",outMessage);
        return outMessage == null ? "登陆失败" : outMessage.toXml();
    }

}
