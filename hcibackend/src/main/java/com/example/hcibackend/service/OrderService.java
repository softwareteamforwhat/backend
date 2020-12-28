package com.example.hcibackend.service;

import com.example.hcibackend.entity.Order;
import com.example.hcibackend.po.OrderInfo;

import java.util.List;

public interface OrderService {
    int saveOrder(OrderInfo orderInfo);

    List<Order> getUserOrder(long uid);

    int returnUserTicket(long uid, String orderId);

    List<Order> getReturnOrder(long uid);

}
