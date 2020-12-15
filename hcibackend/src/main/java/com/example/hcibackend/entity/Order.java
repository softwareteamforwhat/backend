package com.example.hcibackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order")
public class Order {
    private long userId;
    private int state;
    private String movieName;
    private String cinemaName;
    private String startTime;
    private String endTime;
    private String hall;
    private String price;
}
