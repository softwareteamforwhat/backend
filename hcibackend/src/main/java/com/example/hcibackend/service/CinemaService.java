package com.example.hcibackend.service;

import com.example.hcibackend.entity.Cinema;
import com.example.hcibackend.entity.Schedule;
import com.example.hcibackend.po.CinemaSearchForm;
import com.example.hcibackend.vo.CinemaList;
import com.example.hcibackend.vo.MovieSchedule;
import com.example.hcibackend.vo.ScheduleBasic;

import java.util.List;

public interface CinemaService {
    CinemaList getCinemas(CinemaSearchForm cinemaSearchForm);

    Cinema getCinemaInfo(String id);

    List<MovieSchedule> getSchedule(String cinemaId);
}
