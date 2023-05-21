package com.example.capstone.pizza_deilevery_service.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="customersCredentials")
public class CustomerCredentialsEntity {

    @Id
    @Column(name="CUSTOMERID")
    private Integer id;

    @Column (name="login")
    private String login;
    @Column (name="password")
    private String password;
    @Column (name="role")
    private String role;
}
