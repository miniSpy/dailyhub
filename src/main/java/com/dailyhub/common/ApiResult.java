package com.dailyhub.common;

import com.dailyhub.common.myEnum.ResponseEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一结果封装
 * @date 2021/2/2
 * @author ispy
 */

@Data
public class ApiResult<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public ApiResult(Integer code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 声明为泛型方法
     * @return
     */
    public static <T> ApiResult<T> successResult(T data){
        ApiResult<T> success = new ApiResult<>(ResponseEnum.success.getCode(), ResponseEnum.success.getMsg(), data);
        return success;
    }

    public static <T> ApiResult<T> successResult(Integer code,String msg,T data){
        ApiResult<T> success = new ApiResult<>(code,msg,data);
        return success;
    }

    public static <T> ApiResult<T> failResult(String msg){
        ApiResult<T> fail = new ApiResult<>(ResponseEnum.fail.getCode(), msg, null);
        return fail;
    }
}
