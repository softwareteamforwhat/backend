package com.example.hcibackend.dao;

import com.example.hcibackend.entity.Cinema;
import com.example.hcibackend.entity.Movie;
import com.example.hcibackend.entity.Schedule;
import com.example.hcibackend.entity.Service;
import com.example.hcibackend.po.CinemaSearchForm;
import com.example.hcibackend.vo.CinemaBasic;
import com.example.hcibackend.vo.CinemaList;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CinemaDao {

    private final MongoTemplate mongoTemplate;

    public CinemaDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public CinemaList getCinemaList(CinemaSearchForm cinemaSearchForm) {
        String brand = cinemaSearchForm.getBrand();
        String address = cinemaSearchForm.getAddress();
        String tag = cinemaSearchForm.getTag();
        String sortType = cinemaSearchForm.getSortType();
        int page = cinemaSearchForm.getPage();
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("name").regex(brand),Criteria.where("address").regex(address),Criteria.where("services.name").regex(tag));
        query.addCriteria(criteria);
        long count = mongoTemplate.count(query, Cinema.class,"cinema");
        List<Cinema> cinemas = mongoTemplate.find(query.with(Sort.by(Sort.Order.desc(sortType))).skip(10 * (page - 1)).limit(10), Cinema.class,"cinema");
        CinemaList cinemaList = new CinemaList();
        cinemaList.setSum(count);
        List<CinemaBasic> cinemaBasics = new ArrayList<>();
        for(Cinema cinema : cinemas){
            cinemaBasics.add(new CinemaBasic(cinema));
        }
        cinemaList.setCinemaBasics(cinemaBasics);
        return cinemaList;
    }

    public Cinema getCinemaInfo(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("cinemaId").is(id)), Cinema.class,"cinema");
    }

    public List<Schedule> getSchedule(String cinemaId, String movieId) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("cinemaId").is(cinemaId),Criteria.where("movieId").is(movieId));
        return mongoTemplate.find(query.addCriteria(criteria),Schedule.class,"schedule");
    }
}
