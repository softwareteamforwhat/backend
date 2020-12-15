package com.example.hcibackend.dao;

import com.example.hcibackend.entity.Movie;
import com.example.hcibackend.po.MovieSearchForm;
import com.example.hcibackend.vo.MovieBasic;
import com.example.hcibackend.vo.MovieList;
import com.example.hcibackend.vo.MovieRank;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        String state = movieSearchForm.getMovieState();
        String type = movieSearchForm.getMovieType();
        String area = movieSearchForm.getArea();
        String time = movieSearchForm.getYear();
        String sortType = movieSearchForm.getSortType();
        int page = movieSearchForm.getPage();
        List<Movie> movieList;
        Query query = new Query();
        Criteria criteria = new Criteria();
        switch (time) {
            case "全部":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").is(type),
                        Criteria.where("area").is(area));
                break;
            case "2000-2010":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").is(type),
                        Criteria.where("area").is(area),Criteria.where("year").lte(2010).gte(2000));
                break;
            case "90年代":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").is(type),
                        Criteria.where("area").is(area),Criteria.where("year").lte(2000).gte(1990));
                break;
            case "80年代":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").is(type),
                        Criteria.where("area").is(area),Criteria.where("year").lte(1990).gte(1980));
                break;
            case "70年代":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").is(type),
                        Criteria.where("area").is(area),Criteria.where("year").lte(1980).gte(1970));
                break;
            case "更早":
                criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").is(type),
                        Criteria.where("area").is(area),Criteria.where("year").lte(1970));
                break;
        }
        int year = Integer.parseInt(movieSearchForm.getYear());
        criteria.andOperator((Criteria.where("state").is(state)),Criteria.where("type").is(type),
                Criteria.where("area").is(area),Criteria.where("year").is(year));
        query.fields().include("movieId").include("picture").include("c_name").include("e_name").include("type").include("area").include("length").include("year");
        long count = mongoTemplate.count(query,Movie.class,"movie");
        movieList = mongoTemplate.find(query.with(Sort.by(Sort.Order.desc(sortType))).skip(18 * (page - 1)).limit(18),Movie.class,"movie");
        List<MovieBasic> movieBasics = new ArrayList<>();
        for(Movie movie:movieList){
            movieBasics.add(new MovieBasic(movie));
        }
        return new MovieList(movieBasics,count);
    }

    public List<MovieRank> getFollowRank(int page) {
        Query query = new Query();
        query.fields().include("movieId").include("c_name").include("time").include("picture").include("actors").include("follow");
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
        query.fields().include("movieId").include("c_name").include("time").include("picture").include("actors").include("rank");
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
        if(movie!=null){
            List<Long> collectors = movie.getCollectors();
            if(collectors.contains(uid)){
                collectors.remove(uid);
            }else {
                collectors.add(uid);
            }
            Update update = new Update();
            update.set("collectors",collectors);
            mongoTemplate.updateFirst(new Query(Criteria.where("movieId").is(movieId)),update,Movie.class,"movie");
            return 0;
        }else {
            return 1;
        }
    }
}
