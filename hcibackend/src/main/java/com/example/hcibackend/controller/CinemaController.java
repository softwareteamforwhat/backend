package com.example.hcibackend.controller;

import com.example.hcibackend.po.CinemaSearchForm;
import com.example.hcibackend.service.CinemaService;
import com.example.hcibackend.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
public class CinemaController {

    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping("/getCinemas")
    public ResponseVO getCinemas(@RequestBody CinemaSearchForm cinemaSearchForm){
        return ResponseVO.buildSuccess(cinemaService.getCinemas(cinemaSearchForm));
    }

    @GetMapping("/getCinemaInfo")
    public ResponseVO getCinemaInfo(@RequestParam String id){
        return ResponseVO.buildSuccess(cinemaService.getCinemaInfo(id));
    }

    @GetMapping("/getSchedule")
    public ResponseVO getSchedule(@RequestParam String movieId,@RequestParam String cinemaId){
        return ResponseVO.buildSuccess(cinemaService.getSchedule(cinemaId,movieId));
    }
}
