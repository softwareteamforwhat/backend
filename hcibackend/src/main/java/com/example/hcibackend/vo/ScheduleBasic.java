package com.example.hcibackend.vo;

import com.example.hcibackend.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleBasic {
    private int date;//0,1,2
    private String session;
    private String hall;
    private int price;

    public ScheduleBasic(Schedule schedule) {
        this.date = Integer.parseInt(schedule.getSession().split(" ")[0]);
        this.session = schedule.getSession().split(" ")[1];
        this.hall = schedule.getHall();
        this.price = schedule.getPrice();
    }
}

