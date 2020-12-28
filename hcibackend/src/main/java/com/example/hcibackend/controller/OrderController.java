package com.example.hcibackend.controller;

import com.example.hcibackend.config.UserLoginToken;
import com.example.hcibackend.entity.Order;
import com.example.hcibackend.po.OrderInfo;
import com.example.hcibackend.service.OrderService;
import com.example.hcibackend.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

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
    @GetMapping("/getUserOrder")
    public ResponseVO getUserOrder(@RequestParam long uid){
        List<Order> result = orderService.getUserOrder(uid);
        if(result!=null){
            return ResponseVO.buildSuccess(result);
        }else {
            return ResponseVO.buildFailure("获取用户订单失败");
        }
    }

    @UserLoginToken
    @GetMapping("/getReturnOrder")
    public ResponseVO getReturnOrder(@RequestParam long uid){
        List<Order> result = orderService.getReturnOrder(uid);
        if(result!=null){
            return ResponseVO.buildSuccess(result);
        }else {
            return ResponseVO.buildFailure("获取用户订单失败");
        }
    }

    @UserLoginToken
    @PostMapping("/returnUserTicket")
    public ResponseVO returnUserTicket(@RequestParam long uid,@RequestParam String orderId){
        int result = orderService.returnUserTicket(uid,orderId);
        if(result==0){
            return ResponseVO.buildSuccess("退票成功");
        }else {
            return ResponseVO.buildFailure("退票失败");
        }
    }
}
