package com.example.hcibackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVO {
    private int status;
    private String msg;
    private Object data;

    public static ResponseVO buildSuccess(){
        ResponseVO response=new ResponseVO();
        response.setStatus(0);
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
}
