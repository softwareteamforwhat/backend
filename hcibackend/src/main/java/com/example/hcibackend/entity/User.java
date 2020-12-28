package com.example.hcibackend.entity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.hcibackend.vo.MovieCollect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User implements Serializable {

    @Id
    private long uid;

    @Field("nickname")
    private String nickname;

    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Field("avatar")
    private String avatar;

    @Field("collects")
    private List<MovieCollect> movieCollects;

}
