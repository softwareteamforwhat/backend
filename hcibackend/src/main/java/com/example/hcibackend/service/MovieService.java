package com.example.hcibackend.service;

import com.example.hcibackend.entity.Movie;
import com.example.hcibackend.po.MovieSearchForm;
import com.example.hcibackend.vo.MovieList;
import com.example.hcibackend.vo.MovieRank;
import com.example.hcibackend.vo.MovieSearch;

import java.util.List;

public interface MovieService {
    Movie findMovieById(String mid);

    MovieList getMovieList(MovieSearchForm movieSearchForm);

    List<MovieRank> getFollowRank(int page);

    List<MovieRank> getTopRank(int page);

    Movie getMovieInfo(String id);

    List<MovieSearch> searchMovie(String keyword);

    boolean isCollect(long uid, String movieId);
}
