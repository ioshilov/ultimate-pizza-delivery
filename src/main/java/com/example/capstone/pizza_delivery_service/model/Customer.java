package com.example.capstone.pizza_delivery_service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Customer{

    public Customer(Integer id, String name, String surname, String mobile, LocalDate DOB, String email, String homeAddress) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mobile = mobile;
        this.DOB = DOB;
        this.email = email;
        this.homeAddress = homeAddress;
    }

    private Integer id;
    private String name;
    private String surname;

    private String mobile;

    private LocalDate DOB;

    private String email;

    private String homeAddress;

}

