package com.dailyhub.entity;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户类
 * @date 2022/1/1
 * @author ispy
 */
@Data
@Entity
@Table(name = "m_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    private String avatar;

    /**
     * vx openId
     */
    private String openId;

    /**
     * 上一次登录时间
     */
    private Date lastLogin;

    /**
     * 用户创建时间
     */
    private Date createTime;



}
