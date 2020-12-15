package com.example.hcibackend.controller;

import com.example.hcibackend.config.UserLoginToken;
import com.example.hcibackend.po.OrderInfo;
import com.example.hcibackend.service.OrderService;
import com.example.hcibackend.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
public class OrderController {

    @Autowired
    private OrderService orderService;

    @UserLoginToken
    @PostMapping("/saveOrder")
    public ResponseVO saveOrder(@RequestBody OrderInfo orderInfo){
        int state = orderService.saveOrder(orderInfo);
        if(state == 0){
            return ResponseVO.buildSuccess("生成订单成功");
        }else {
            return ResponseVO.buildFailure("生成订单失败");
        }
    }

    @UserLoginToken
    @GetMapping("/getUserExpense")
    public ResponseVO getUserExpense(@RequestParam long uid){
        return ResponseVO.buildSuccess(orderService.getExpense(uid));
    }
}
