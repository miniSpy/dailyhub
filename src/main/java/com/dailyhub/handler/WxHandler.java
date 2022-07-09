package com.dailyhub.handler;

import com.dailyhub.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class WxHandler implements WxMpMessageHandler {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginHandler loginHandler;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {

        WxMpXmlOutTextMessage outMessage = new WxMpXmlOutTextMessage();
        outMessage.setFromUserName(wxMpXmlMessage.getToUser());
        outMessage.setToUserName(wxMpXmlMessage.getFromUser());
        outMessage.setCreateTime(System.currentTimeMillis() / 1000L);
        //获取openId
        String openId = wxMpXmlMessage.getFromUser();
        //用户在公众号输入的内容
        String content = wxMpXmlMessage.getContent();
        log.info("com.dailyhub.handler.WxHandler.handle.openId and content:{},{}",openId,content);

        String lower = StringUtils.lowerCase("NT");

        if(!StringUtils.isBlank(content) && (content.startsWith(lower) || content.startsWith("NT")) && content.length() == 6){
//            boolean hasKey = redisUtil.hasKey(content);
            if (!redisUtil.hasKey(content))
            {
                log.info("验证码过期");
                outMessage.setContent("校验不正确,验证码过期");
                return outMessage;
            }
            String loginRes = loginHandler.checkUser(openId,content);
            log.info("com.dailyhub.handler.LoginHandler.checkUser.res：{}",loginRes);
            outMessage.setContent(loginRes);
            return outMessage;
        }

        outMessage.setContent("校验不正确,验证码错误");
        return outMessage;
    }
}
