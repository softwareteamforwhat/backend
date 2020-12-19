package com.example.hcibackend.serviceImp;

import com.example.hcibackend.dao.OrderDao;
import com.example.hcibackend.entity.Order;
import com.example.hcibackend.po.OrderInfo;
import com.example.hcibackend.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    private final OrderDao orderDao;

    public OrderServiceImp(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public int saveOrder(OrderInfo orderInfo) {
        return orderDao.saveOrder(orderInfo);
    }

    @Override
    public List<Order> getUserOrder(long uid) {
        return orderDao.getUserOrder(uid);
    }

    @Override
    public int returnUserTicket(long uid, String orderId) {
        return orderDao.returnUserTicket(uid,orderId);
    }
}
