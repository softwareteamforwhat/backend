package com.example.hcibackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVO{
    private int status;
    private String msg;
    private Object data;

    public static ResponseVO buildSuccess(String msg){
        ResponseVO response=new ResponseVO();
        response.setStatus(0);
        response.setMsg(msg);
        return response;
    }

    public static ResponseVO buildSuccess(Object data){
        ResponseVO response=new ResponseVO();
        response.setStatus(0);
        response.setData(data);
        return response;
    }

    public static ResponseVO buildFailure(String msg){
        ResponseVO response = new ResponseVO();
        response.setStatus(1);
        response.setMsg(msg);
        return response;
    }

    public static ResponseVO buildFailure(int status,String msg){
        ResponseVO response = new ResponseVO();
        response.setStatus(status);
        response.setMsg(msg);
        return response;
    }
}
