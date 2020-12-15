package com.example.hcibackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieList{
    private List<MovieBasic> movieBasics;
    private long sum;
}