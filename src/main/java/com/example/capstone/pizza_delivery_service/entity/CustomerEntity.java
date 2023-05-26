package com.example.capstone.pizza_delivery_service.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@Table(name="customers")
public class CustomerEntity {

    @Id
    @Column(name="id")
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

    @OneToOne(mappedBy = "customerEntity", cascade =  CascadeType.ALL)
    private CustomersCredentialsEntity CustomersCredentialsEntity;

    public CustomerEntity() {
    }
}
