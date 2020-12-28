package com.example.hcibackend.controller;

import com.example.hcibackend.config.UserLoginToken;
import com.example.hcibackend.po.MovieSearchForm;
import com.example.hcibackend.service.MovieService;
import com.example.hcibackend.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

@RestController()
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movie")
    public ResponseVO findMovieById(@RequestParam String movieId){
        return ResponseVO.buildSuccess(movieService.findMovieById(movieId));
    }

    @PostMapping("/getMovieList")
    public ResponseVO getMovieList(@RequestBody MovieSearchForm movieSearchForm){
        return ResponseVO.buildSuccess(movieService.getMovieList(movieSearchForm));
    }

    @GetMapping("/getFollowRank")
    public ResponseVO getFollowRank(@RequestParam int page){
        return ResponseVO.buildSuccess(movieService.getFollowRank(page));
    }

    @GetMapping("/getTopRank")
    public ResponseVO getTopRank(@RequestParam int page){
        return ResponseVO.buildSuccess(movieService.getTopRank(page));
    }

    @GetMapping("/movieInfo")
    public ResponseVO getMovieInfo(@RequestParam String id){
        return ResponseVO.buildSuccess(movieService.getMovieInfo(id));
    }

    @GetMapping("/getSearch")
    public ResponseVO searchMovie(@RequestParam String keyword){
        return ResponseVO.buildSuccess(movieService.searchMovie(keyword));
    }

    @UserLoginToken
    @GetMapping("/isCollect")
    public ResponseVO isCollect(@RequestParam long uid,@RequestParam String movieId){
        return ResponseVO.buildSuccess(movieService.isCollect(uid,movieId));
    }
}
