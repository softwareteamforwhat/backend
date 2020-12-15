package com.example.hcibackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cinema")
public class Cinema implements Serializable {
    @Field(name = "cinemaId")
    private String cinemaId;

    @Field(name = "name")
    private String name;

    @Field(name = "picture")
    private String picture;

    @Field(name = "address")
    private String address;

    @Field(name = "phone")
    private String phone;

    @Field(name = "services")
    private List<Service> services;
}
