package com.dailyhub.service.Impl;

import com.dailyhub.dto.UserDto;
import com.dailyhub.entity.User;
import com.dailyhub.repository.UserRepository;
import com.dailyhub.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDto login(String code, String openId) {
        User user = userRepository.findByOpenId(openId);
        if(ObjectUtils.isEmpty(user)){
            User newUser = new User();
            newUser.setUsername("demo"+ UUID.randomUUID());
            newUser.setCreateTime(new Date());
            newUser.setOpenId(openId);
            user=newUser;
        }else {
            user.setLastLogin(new Date());
        }
        userRepository.save(user);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }
}
