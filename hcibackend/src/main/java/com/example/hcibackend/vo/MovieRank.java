package com.example.hcibackend.vo;

import com.example.hcibackend.entity.Actor;
import com.example.hcibackend.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRank {
    private int index;
    private String movieId;
    private String picture;
    private String name;
    private List<String> actors;
    private String time;
    private int follow;
    private int rank;

    public MovieRank(int index, Movie movie,String type) {
        if(type.equals("follow")){
            this.index = index;
            this.movieId = movie.getMovieId();
            this.picture = movie.getPicture();
            this.name = movie.getC_name();
            this.actors = movie.getActors().stream().map(Actor::getName).collect(Collectors.toList());
            this.time = movie.getTime();
            this.follow = movie.getFollow();
            this.rank = 0;
        }else {
            this.index = index;
            this.movieId = movie.getMovieId();
            this.picture = movie.getPicture();
            this.name = movie.getC_name();
            this.actors = movie.getActors().stream().map(Actor::getName).collect(Collectors.toList());
            this.time = movie.getTime();
            this.follow = 0;
            this.rank = movie.getRank();
        }

    }
}