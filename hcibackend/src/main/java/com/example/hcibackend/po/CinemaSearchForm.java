package com.example.hcibackend.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaSearchForm {
    private String brand;
    private String address;
    private String tag;
    private String sortType;
    private String movieId;
    private int page;
}
