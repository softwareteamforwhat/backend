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
public class MovieSearch {
    private String movieId;
    private String picture;
    private String c_name;
    private String e_name;
    private List<String> type;
    private List<String> actors;
    private String date;
    private double rank;

    public MovieSearch(Movie movie) {
        this.movieId = movie.getMovieId();
        this.picture = movie.getPicture();
        this.c_name = movie.getC_name();
        this.e_name = movie.getE_name();
        this.type = movie.getType();
        if(movie.getActors().size()>3){
            this.actors = movie.getActors().stream().map(Actor::getName).collect(Collectors.toList()).subList(0,3);
        }else {
            this.actors = movie.getActors().stream().map(Actor::getName).collect(Collectors.toList());
        }
        this.date = movie.getDate();
        this.rank = movie.getRank();
    }
}
