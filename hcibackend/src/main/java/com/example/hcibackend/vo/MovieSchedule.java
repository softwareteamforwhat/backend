package com.example.hcibackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieSchedule {
    private MovieBasic movieBasic;
    private List<ScheduleBasic> schedules;
}
