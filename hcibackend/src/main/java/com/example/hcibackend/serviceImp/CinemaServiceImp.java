package com.example.hcibackend.serviceImp;

import com.example.hcibackend.dao.CinemaDao;
import com.example.hcibackend.entity.Cinema;
import com.example.hcibackend.po.CinemaSearchForm;
import com.example.hcibackend.service.CinemaService;
import com.example.hcibackend.vo.CinemaList;
import com.example.hcibackend.vo.MovieSchedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaServiceImp implements CinemaService {
    private final CinemaDao cinemaDao;

    public CinemaServiceImp(CinemaDao cinemaDao) {
        this.cinemaDao = cinemaDao;
    }

    @Override
    public CinemaList getCinemas(CinemaSearchForm cinemaSearchForm) {
        return cinemaDao.getCinemaList(cinemaSearchForm);
    }

    @Override
    public Cinema getCinemaInfo(String id) {
        return cinemaDao.getCinemaInfo(id);
    }

    @Override
    public List<MovieSchedule> getSchedule(String cinemaId) {
        return cinemaDao.getSchedule(cinemaId);
    }
}
