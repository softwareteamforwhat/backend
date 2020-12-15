package com.example.hcibackend.service;

import com.example.hcibackend.po.OrderInfo;
import com.example.hcibackend.vo.OrderBasic;

import java.util.List;

public interface OrderService {
    int saveOrder(OrderInfo orderInfo);

    List<OrderBasic> getExpense(long uid);
}
