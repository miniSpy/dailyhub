package com.dailyhub.config;

import com.dailyhub.handler.WxHandler;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wx")
public class WxConfig {

    private String appID;

    private String appsecret;

    private String token;

    @Autowired
    private WxHandler wxHandler;

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(appID);
        wxMpConfigStorage.setSecret(appsecret);
        wxMpConfigStorage.setToken(token);
        return wxMpConfigStorage;
    }

    @Bean
    public WxMpService wxMpService(){
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    //微信路由
    @Bean
    public WxMpMessageRouter wxMpMessageRouter(){
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService());
        router.rule().async(false).msgType(WxConsts.XmlMsgType.TEXT).handler(wxHandler).end();
        return router;
    }
}
