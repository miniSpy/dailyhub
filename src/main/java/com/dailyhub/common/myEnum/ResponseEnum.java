package com.dailyhub.common.myEnum;

/**
 * 枚举类
 * @date 2021/2/2
 * @author ispy
 */
public enum ResponseEnum {

    success(200,"success"),
    fail(400,"fail");

    private Integer code;

    private String msg;

    private ResponseEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
