package com.example.hcibackend.vo;

import com.example.hcibackend.entity.Cinema;
import com.example.hcibackend.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaBasic {
    private String cinemaId;
    private String name;
    private String picture;
    private String address;
    private String phone;
    private int price;
    private int distance;
    private List<Service> services;

    public CinemaBasic(Cinema cinema) {
        this.address = cinema.getAddress();
        this.cinemaId = cinema.getCinemaId();
        this.picture = cinema.getPicture();
        this.distance = cinema.getDistance();
        this.price = cinema.getPrice();
        this.name = cinema.getName();
        this.phone = cinema.getPhone();
        this.services = cinema.getServices();
    }
}
