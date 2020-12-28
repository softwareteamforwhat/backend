package com.example.hcibackend.dao;

import com.example.hcibackend.entity.Movie;
import com.example.hcibackend.entity.User;
import com.example.hcibackend.po.MovieSearchForm;
import com.example.hcibackend.vo.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieDao {

    private final MongoTemplate mongoTemplate;

    public MovieDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Movie findMovieById(String mid) {
        return mongoTemplate.findOne(new Query(Criteria.where("movieId").is(mid)),Movie.class,"movie");
    }

    public MovieList getMovieList(MovieSearchForm movieSearchForm) {
        int state = movieSearchForm.getMovieState();
        String type = movieSearchForm.getMovieType();
        String area = movieSearchForm.getArea();
        String time = movieSearchForm.getYear();
        String sortType = movieSearchForm.getSortType();
        switch (sortType){
            case "热门":
                sortType = "follow";
                break;
            case "评分":
                sortType = "rank";
                break;
            case "时间":
                sortType = "time";
                break;
        }
        int page = movieSearchForm.getPage();
        List<Movie> movieList;
        Query query = new Query();
        Criteria criteria = new Criteria();
        switch (time) {
            case "全部":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").regex(type),
                        Criteria.where("area").regex(area));
                break;
            case "2000-2010":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").regex(type),
                        Criteria.where("area").regex(area),Criteria.where("year").lte(2010).gt(2000));
                break;
            case "90年代":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").regex(type),
                        Criteria.where("area").regex(area),Criteria.where("year").lte(2000).gt(1990));
                break;
            case "80年代":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").regex(type),
                        Criteria.where("area").regex(area),Criteria.where("year").lte(1990).gt(1980));
                break;
            case "70年代":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").regex(type),
                        Criteria.where("area").regex(area),Criteria.where("year").lte(1980).gt(1970));
                break;
            case "更早":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").regex(type),
                        Criteria.where("area").regex(area),Criteria.where("year").lte(1970));
                break;
            default:
                int year = Integer.parseInt(movieSearchForm.getYear());
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").regex(type),
                        Criteria.where("area").regex(area),Criteria.where("year").is(year));
                break;
        }
        query.addCriteria(criteria);
        long count = mongoTemplate.count(query,Movie.class,"movie");
        movieList = mongoTemplate.find(query.with(Sort.by(Sort.Order.desc(sortType))).skip(15 * (page - 1)).limit(15),Movie.class,"movie");
        List<MovieBasic> movieBasics = new ArrayList<>();
        for(Movie movie:movieList){
            movieBasics.add(new MovieBasic(movie));
        }
        return new MovieList(movieBasics,count);
    }

    public List<MovieRank> getFollowRank(int page) {
        Query query = new Query();
        query.fields().include("movieId").include("c_name").include("date").include("picture").include("actors").include("follow");
        List<Movie> movieList = mongoTemplate.find(query.with(Sort.by(Sort.Order.desc("follow"))).skip(10 * (page - 1)).limit(10),Movie.class,"movie");
        List<MovieRank> movieRanks = new ArrayList<>();
        int index = 10 * (page - 1);
        for(Movie movie:movieList){
            index++;
            movieRanks.add(new MovieRank(index,movie,"follow"));
        }
        return movieRanks;
    }


    public List<MovieRank> getTopRank(int page) {
        Query query = new Query();
        query.fields().include("movieId").include("c_name").include("date").include("picture").include("actors").include("rank");
        List<Movie> movieList = mongoTemplate.find(query.with(Sort.by(Sort.Order.desc("rank"))).skip(10 * (page - 1)).limit(10),Movie.class,"movie");
        List<MovieRank> movieRanks = new ArrayList<>();
        int index = 10 * (page - 1);
        for(Movie movie:movieList){
            index++;
            movieRanks.add(new MovieRank(index,movie,"rank"));
        }
        return movieRanks;
    }

    public Movie getMovieInfo(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("movieId").is(id)),Movie.class,"movie");
    }
    /**
     * 修改电影收藏状态
     * @param uid 用户id
     * @param movieId 电影id
     * @return state
     */
    public int changeFollow(Long uid, String movieId) {
        Movie movie = mongoTemplate.findOne(new Query(Criteria.where("movieId").is(movieId)), Movie.class,"movie");
        User user = mongoTemplate.findOne(new Query(Criteria.where("_id").is(uid)),User.class,"user");
        if(movie!=null && user!=null){
            List<Long> collectors = movie.getCollectors();
            if(collectors.contains(uid)){
                collectors.remove(uid);
            }else {
                collectors.add(uid);
            }
            Update update1 = new Update();
            update1.set("collectors",collectors);
            mongoTemplate.updateFirst(new Query(Criteria.where("movieId").is(movieId)),update1,Movie.class,"movie");

            List<MovieCollect> collects = user.getMovieCollects();
            List<String> movieIds = collects.stream().map(MovieCollect::getMovieId).collect(Collectors.toList());
            if(movieIds.contains(movieId)){
                collects.removeIf(movieCollect -> movieCollect.getMovieId().equals(movieId));
            }else {
                collects.add(new MovieCollect(movie));
            }
            Update update2 = new Update();
            update2.set("collects",collects);
            mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(uid)),update2,User.class);
            return 0;
        }else {
            return 1;
        }
    }

    public List<MovieSearch> searchMovie(String keyword) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("c_name").regex(keyword),Criteria.where("e_name").regex(keyword),Criteria.where("actors.name").regex(keyword),Criteria.where("director.name").regex(keyword));
        List<Movie> movies = mongoTemplate.find(query.addCriteria(criteria).limit(10),Movie.class,"movie");
        List<MovieSearch>  movieSearches = new ArrayList<>();
        for(Movie movie:movies){
            movieSearches.add(new MovieSearch(movie));
        }
        return movieSearches;

    }

    public boolean isCollect(long uid, String movieId) {
        Movie movie = mongoTemplate.findOne(new Query(Criteria.where("movieId").is(movieId)),Movie.class,"movie");
        if(movie!=null){
            List<Long> collectors = movie.getCollectors();
            return collectors.contains(uid);
        }
        return false;
    }
}
