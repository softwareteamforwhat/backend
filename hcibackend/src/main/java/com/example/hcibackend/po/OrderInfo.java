package com.example.hcibackend.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    private long uid;
    private String scheduleId;
    private List<Map<Integer,Integer>> seats;
}
