package com.example.capstone.pizza_delivery_service.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min=2, max=50)
    @NotNull
    private String name;
    @Column (name="SURNAME")
    @Size(min=2, max=50)
    @NotNull
    private String surname;
    @Column (name="MOBILE")
    @Size(min=2, max=20)
    @NotNull
    private String mobile;
    @Column (name="DOB")
    @NotNull
    private LocalDate DOB;
    @Column (name="EMAIL")
    @Size(min=5, max=50)
    @NotNull
    @Email
    private String email;
    @Column (name="HOMEADDRESS")
    @NotNull
    @Size(min=5, max=200)
    private String homeAddress;

    @OneToOne(mappedBy = "customerEntity", cascade =  CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private CustomersCredentialsEntity customersCredentialsEntity;


    public CustomerEntity() {
    }
}
