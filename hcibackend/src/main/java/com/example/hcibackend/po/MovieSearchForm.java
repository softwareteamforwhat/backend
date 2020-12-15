package com.example.hcibackend.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieSearchForm {
    private String movieState;
    private String movieType;
    private String area;
    private String year;
    private String sortType;
    private int page;
}
