package com.dailyhub.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.ServletRequestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Resource
    HttpServletRequest request;

    Pageable getPageable(){
        int page = ServletRequestUtils.getIntParameter(request, "page", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 10);
        return PageRequest.of(page-1 , size);
    }

}
