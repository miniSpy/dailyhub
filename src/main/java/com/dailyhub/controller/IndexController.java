package com.dailyhub.controller;

import cn.hutool.core.lang.Assert;
import com.dailyhub.annotation.Login;
import com.dailyhub.common.ApiResult;
import com.dailyhub.dto.SearchDto;
import com.dailyhub.dto.TimeTitleDto;
import com.dailyhub.dto.UserDto;
import com.dailyhub.entity.UserCollect;
import com.dailyhub.service.SearchService;
import com.dailyhub.service.UserCollectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class IndexController extends BaseController{

    @Value("${server.domain}")
    String url;

    @Autowired
    private UserCollectService userCollectService;

    @Autowired
    private SearchService searchService;

    @Login
    @RequestMapping("/")
    public String index(){
        UserDto userInfo = (UserDto)request.getSession().getAttribute("userInfo");
        request.setAttribute("username",userInfo.getUsername());
        request.setAttribute("useravatar",userInfo.getAvatar());
        request.setAttribute("openId",userInfo.getOpenId());
        List<TimeTitleDto> timeTitleDtoList = userCollectService.getCollectByOpenId(userInfo.getOpenId());
        request.setAttribute("timeTitleList",timeTitleDtoList);
        log.info("一二级时间目录构成:{}",timeTitleDtoList);
        return "index";
    }

    @ResponseBody
    @GetMapping("/collects/{openId}/{timeTitle}")
    public ApiResult<List<UserCollect>> getUserCollect(@PathVariable(name = "openId") String openId,@PathVariable(name = "timeTitle") String timeTitle){
        if(StringUtils.isBlank(openId))
        {
            log.info("getUserCollect.openId为空");
            return ApiResult.failResult("openId为空");
        }
        List<UserCollect> userCollects =  userCollectService.getUsrCollect(openId,timeTitle,getPageable());
        log.info("com.dailyhub.controller.IndexController.getUserCollect.userCollects:{}",userCollects);
        return ApiResult.successResult(userCollects);
    }

    @Login
    @ResponseBody
    @PostMapping("/collects/delete")
    public ApiResult deleteCollect(@RequestParam(name = "id") Long id){
        UserCollect collectById = userCollectService.getCollectById(id);
        Assert.notNull(collectById,"不存在此条数据");
        UserDto userInfo = (UserDto)request.getSession().getAttribute("userInfo");
        log.info("{}",userInfo);
        if(!collectById.getOpenId().equals(userInfo.getOpenId()))
            return ApiResult.failResult("没有权限修改此条数据");
        log.info("com.dailyhub.controller.IndexController.deleteCollect.id:{}",id);
        userCollectService.deleteById(id);
        return ApiResult.successResult("删除成功");
    }

    @Login
    @GetMapping("/collects/edit")
    public String editPage(UserCollect userCollect){
        String js="javascript:(function () {var site='"+url+"/collects/edit?title='+encodeURIComponent(document.title)+'&url='+encodeURIComponent(document.URL);var win=window.open(site,'_blank');win.focus();})()";
        request.setAttribute("js",js);
        if(userCollect.getId() == null){
            UserCollect collect = new UserCollect();
            collect.setTitle(userCollect.getTitle());
            collect.setUrl(userCollect.getUrl());
            request.setAttribute("collect",collect);
            return "collect-edit";
        }
        UserCollect collectById = userCollectService.getCollectById(userCollect.getId());
        Assert.notNull(collectById,"不存在此收藏");
        log.info("com.dailyhub.controller.IndexController.editPage.collectById:{}",collectById);
        request.setAttribute("collect",collectById);
        return "collect-edit";
    }

    @ResponseBody
    @Login
    @PostMapping("/collects/save")
    public ApiResult editOrSave(UserCollect userCollect){
        if(userCollect.getTitle()==null)
            ApiResult.failResult("openId or Title参数为空");
        //获取用户信息
        UserDto userInfo = (UserDto)request.getSession().getAttribute("userInfo");
        log.info("{}",userInfo);
        userCollectService.editOrSave(userCollect,userInfo);
        return ApiResult.successResult(200,"修改成功",null);
    }

    @GetMapping("/collects/square")
    public String collectsSquare(){
        UserDto userInfo = (UserDto)request.getSession().getAttribute("userInfo");
        if(ObjectUtils.isEmpty(userInfo))
        {
            request.setAttribute("openId","null");
        }
        request.setAttribute("openId",userInfo.getOpenId());
        log.info("{}",userInfo.getOpenId());
        return "collect-square";
    }

    @ResponseBody
    @GetMapping("/collects/all")
    public ApiResult getAllCollects(SearchDto searchDto){
        //关键字查询不为空，通过es搜索结果
        if(!StringUtils.isBlank(searchDto.getQ())){
            log.info("keyword:{}",searchDto.getQ());
            List<UserCollect> userCollects = searchService.searchByKeyword(searchDto);
            log.info("com.dailyhub.controller.IndexController.getAllCollects.data:{}",userCollects);
            return ApiResult.successResult(userCollects);
        }
        List<UserCollect> allCollects = userCollectService.getAllCollects(getPageable());
        log.info("com.dailyhub.controller.IndexController.getAllCollects.data:{}",allCollects);
        return ApiResult.successResult(allCollects);
    }


    @GetMapping("/search")
    public String searchPage(SearchDto searchDto){
        String message="正在搜索公开【收藏广场】的收藏记录";
        if(!StringUtils.isBlank(searchDto.getOpenId())){
            message = "正在搜索用户["+searchDto.getOpenId()+"]的收藏记录";
        }
        request.setAttribute("message",message);
        request.setAttribute("q",searchDto.getQ());
        return "collect-square";
    }
}
