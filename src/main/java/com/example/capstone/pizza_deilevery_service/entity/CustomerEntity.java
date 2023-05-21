package com.example.capstone.pizza_deilevery_service.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="customers")
public class CustomerEntity {

    @Id
    @Column(name="CUSTOMERID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name="NAME")
    private String name;
    @Column (name="SURNAME")
    private String surname;
    @Column (name="MOBILE")
    private String mobile;
    @Column (name="DOB")
    private LocalDate DOB;
    @Column (name="EMAIL")
    private String email;
    @Column (name="HOMEADDRESS")
    private String homeAddress;

}
