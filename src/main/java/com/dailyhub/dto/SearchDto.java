package com.dailyhub.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class SearchDto {

    /**
     * search String
     */
    private String q;

    /**
     * openId
     */
    private String openId;

    private Integer page;

    private Integer size;



}
