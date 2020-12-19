package com.example.hcibackend.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo implements Serializable {
    private long uid;
    private String movieId;
    private String cinema;
    private String purchaseTime;
    private String session;
    private String lang;
    private String hall;
    private int unitPrice;
    private Map<Integer,Integer> seats;
}
