package com.example.hcibackend.serviceImp;

import com.example.hcibackend.entity.Order;
import com.example.hcibackend.entity.Schedule;
import com.example.hcibackend.po.OrderInfo;
import com.example.hcibackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;



@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public int saveOrder(OrderInfo orderInfo) {
        Order order = new Order();
        order.setState(0);
        Schedule schedule = mongoTemplate.findOne(new Query(Criteria.where("scheduleId").is(orderInfo.getScheduleId())),Schedule.class,"schedule");
        if(schedule!=null){
            order.setStartTime(schedule.getStartTime());
            order.setEndTime(schedule.getEndTime());
            order.setCinemaName(schedule.getCinema());
            order.setMovieName(schedule.getMovie());
            order.setHall(schedule.getHall());
            order.setPrice(schedule.getPrice());
            order.setUserId(orderInfo.getUid());
            mongoTemplate.insert(order,"order");
            return 0;
        }else {
            return 1;
        }
    }
}
