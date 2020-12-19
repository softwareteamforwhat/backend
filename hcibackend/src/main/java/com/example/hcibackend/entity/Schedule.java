package com.example.hcibackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "schedule")
public class Schedule{
    private String scheduleId;
    private String movieId;
    private String movie;
    private String cinemaId;
    private String cinema;
    private String session;
    private String hall;
    private String price;
}
