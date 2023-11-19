package com.example20231016.RBAC_server;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;


    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = 200;
        result.data = data;
        result.msg = "操作成功";
        return result;
    }
    public static <T> CommonResult<T> authFailed(String message) {
        CommonResult<T> result = new CommonResult<>();
        result.code = 401;
        result.msg = "认证失败";
        return result;
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }
}