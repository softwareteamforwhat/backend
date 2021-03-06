package com.example.hcibackend.vo;

import com.example.hcibackend.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieBasic {
    private String movieId;
    private int state;
    private String picture;
    private String c_name;
    private String e_name;
    private List<String> type;
    private String area;
    private String length;
    private String date;

    public MovieBasic(Movie movie) {
        this.movieId = movie.getMovieId();
        this.state = movie.getState();
        this.picture = movie.getPicture();
        this.c_name = movie.getC_name();
        this.e_name = movie.getE_name();
        this.type = movie.getType();
        this.area = movie.getArea();
        this.length = movie.getLength();
        this.date = movie.getDate();
    }
}
