package com.example.hcibackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movie")
public class Movie implements Serializable {

    @Field(name = "movieId")
    private String movieId;

    @Field(name = "picture")
    private String picture;

    @Field(name = "c_name")
    private String c_name;

    @Field(name = "e_name")
    private String e_name;

    @Field(name = "type")
    private List<String> type;

    @Field(name = "area")
    private String area;

    @Field(name = "length")
    private String length;

    @Field(name = "time")
    private String time;

    @Field(name = "description")
    private String description;

    @Field(name = "director")
    private Director director;

    @Field(name = "actors")
    private List<Actor> actors;

    @Field(name = "state")
    private int state;

    @Field(name = "collectors")
    private List<Long> collectors;

    @Field(name = "follow")
    private int follow;

    @Field(name = "rank")
    private int rank;
}


