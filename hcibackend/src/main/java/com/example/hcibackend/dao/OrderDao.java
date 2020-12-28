package com.example.hcibackend.dao;

import com.example.hcibackend.entity.Movie;
import com.example.hcibackend.entity.Order;
import com.example.hcibackend.po.OrderInfo;
import com.example.hcibackend.vo.MovieBasic;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrderDao {

    private final MongoTemplate mongoTemplate;

    public OrderDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public int saveOrder(OrderInfo orderInfo) {
        Order order = new Order();
        order.setUid(orderInfo.getUid());
        long count = mongoTemplate.count(new Query(Criteria.where("uid").is(orderInfo.getUid())),Order.class,"order");
        order.setOrderId("order"+ (count + 1));
        order.setState(0);
        order.setPrice(orderInfo.getUnitPrice());
        order.setHall(orderInfo.getHall());
        order.setCinema(orderInfo.getCinema());
        order.setLang(order.getLang());
        order.setAmount(orderInfo.getSeats().size());
        order.setPurchaseTime(orderInfo.getPurchaseTime());
        order.setOperateTime(System.currentTimeMillis());
        order.setSession(orderInfo.getSession());
        List<String> seats = new ArrayList<>();
        Map<Integer,Integer> seatsMap = orderInfo.getSeats();
        for(Integer x:seatsMap.keySet()){
            seats.add(x+"排"+seatsMap.get(x)+"座");
        }
        order.setSeats(seats);
        Movie movie=mongoTemplate.findOne(new Query(Criteria.where("movieId").is(orderInfo.getMovieId())),Movie.class,"movie");
        if(movie!=null){
            order.setMovieBasic(new MovieBasic(movie));
            mongoTemplate.insert(order,"order");
            return 0;
        }
        return 1;
    }

    public List<Order> getUserOrder(long uid) {
        return mongoTemplate.find(new Query(Criteria.where("uid").is(uid).and("state").is(0)).with(Sort.by(Sort.Order.desc("operateTime"))),Order.class,"order");
    }

    public int returnUserTicket(long uid, String orderId) {
        Update update = new Update();
        update.set("state",1);
        update.set("operateTime",System.currentTimeMillis());
        try {
            mongoTemplate.updateFirst(new Query(Criteria.where("uid").is(uid).and("orderId").is(orderId)),update,Order.class);
            return 0;
        }catch (Exception e){
            return 1;
        }
    }

    public List<Order> getReturnOrder(long uid) {
        return mongoTemplate.find(new Query(Criteria.where("uid").is(uid).and("state").is(1)).with(Sort.by(Sort.Order.desc("operateTime"))),Order.class,"order");
    }
}
