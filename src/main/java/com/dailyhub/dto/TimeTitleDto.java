package com.dailyhub.dto;

import lombok.Data;

import java.util.List;

/**
 * 侧边栏时间
 * @author ispy
 * @date 2022/2/2
 */
@Data
public class TimeTitleDto {

    /**
     *
     */
    private String firstTitle;

    /**
     *
     */
    private List<TimeTitleDto> secondTitle;
}
