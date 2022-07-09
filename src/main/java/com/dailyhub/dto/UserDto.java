package com.dailyhub.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * UserDto
 */
@Data
public class UserDto implements Serializable {

    /**
     * 用户名
     */
    private String username;

    private String avatar;

    /**
     * vx openId
     */
    private String openId;


}
