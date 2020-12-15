package com.example.hcibackend.service;

import com.example.hcibackend.entity.Cinema;
import com.example.hcibackend.entity.Schedule;
import com.example.hcibackend.po.CinemaSearchForm;
import com.example.hcibackend.vo.CinemaList;

import java.util.List;

public interface CinemaService {
    CinemaList getCinemas(CinemaSearchForm cinemaSearchForm);

    Cinema getCinemaInfo(String id);

    List<Schedule> getSchedule(String cinemaId, String movieId);
}
