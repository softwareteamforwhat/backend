package com.example.hcibackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movie")
public class Movie {
    private long movieId;
    private String picture;
    private String name;
    private String name2;
    private List<String> type;
    private String place;
    private String length;
    private String time;
    private String description;
    private Director director;
    private List<Actor> actors;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Director{
    private String link;
    private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Actor{
    private String link;
    private String name;
    private String role;
}
