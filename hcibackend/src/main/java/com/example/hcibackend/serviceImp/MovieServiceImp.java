package com.example.hcibackend.serviceImp;

import com.example.hcibackend.dao.MovieDao;
import com.example.hcibackend.entity.Movie;
import com.example.hcibackend.po.MovieSearchForm;
import com.example.hcibackend.service.MovieService;
import com.example.hcibackend.vo.MovieList;
import com.example.hcibackend.vo.MovieRank;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImp implements MovieService {

    private final MovieDao movieDao;

    public MovieServiceImp(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public Movie findMovieById(String mid) {
        return movieDao.findMovieById(mid);
    }

    @Override
    public MovieList getMovieList(MovieSearchForm movieSearchForm) {
        return movieDao.getMovieList(movieSearchForm);
    }

    @Override
    public List<MovieRank> getFollowRank(int page) {
        return movieDao.getFollowRank(page);
    }

    @Override
    public List<MovieRank> getTopRank(int page) {
        return movieDao.getTopRank(page);
    }

    @Override
    public Movie getMovieInfo(String id) {
        return movieDao.getMovieInfo(id);
    }
}
