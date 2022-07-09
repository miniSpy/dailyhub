package com.dailyhub.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 收藏夹类
 */
@Data
@Entity
@Table(name = "m_collect")
public class UserCollect implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String  title;

    private String info;

    //是否公开 默认不公开 0-公开 1-不公开
    private Integer personal = 0;

    /**
     * 用户openId 关联用户
     */
    private String openId;

    private Date createTime;

    private Date collected;

    private String url;

}
