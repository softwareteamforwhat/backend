package com.example.hcibackend.entity;

import com.example.hcibackend.vo.MovieBasic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order")
public class Order {
    private String orderId;
    private long uid;
    private int state;
    private MovieBasic movieBasic;
    private String cinema;
    private String session;
    private String purchaseTime;
    private String hall;
    private int price;
    private String lang;
    private int amount;
    private List<String> seats;
}
