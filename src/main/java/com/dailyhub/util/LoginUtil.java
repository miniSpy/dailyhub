package com.dailyhub.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginUtil {

  @Autowired
  private RedisUtil redisUtil;

  public List<String> getCodeAndTicket(){

      String code = "NT" + RandomUtil.randomNumbers(4);

      while(ObjectUtil.isNotEmpty(redisUtil.get(code))){
          code = "NT" + RandomUtil.randomNumbers(4);
      }

      //生成ticket
      String ticket =  RandomUtil.randomString(32);
      //存入redis
      redisUtil.set(code,ticket,60*3);
      ArrayList<String> res = new ArrayList<String>();
      res.add(code);
      res.add(ticket);
      return res;
  }


}
