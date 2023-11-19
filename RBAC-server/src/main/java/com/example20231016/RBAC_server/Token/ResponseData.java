package com.example20231016.RBAC_server.Token;


import lombok.Data;

@Data
public class ResponseData {
    private int code;
    private String msg;
    private dataData data;

    public static ResponseData success(int code ,String message ) {
        ResponseData result = new ResponseData();
        result.code = code;
        result.data = dataData.success(code,message);
        result.msg = message;
        return result;
    }
}
@Data
class dataData{
    int code;
    String msg;

    public static dataData success(int code ,String message ) {
        dataData result = new dataData();
        result.code = code;
        result.msg = message;
        return result;
    }

}
