package com.example.capstone.pizza_delivery_service.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@Table(name="customerscredentials")
public class CustomersCredentialsEntity {

    @Id
    @Column(name="customerid")
    private Integer customerid;
    @Column (name="username",nullable = false,unique = true)
    private String username;
    @Column (name="password",nullable = false)
    private String password;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid")
    private CustomerEntity customerEntity;

    @OneToMany (mappedBy = "customersCredentialsEntity",fetch = FetchType.LAZY)
    private List<AuthGroupEntity> authGroupEntityList;

    public CustomersCredentialsEntity() {
    }
}
