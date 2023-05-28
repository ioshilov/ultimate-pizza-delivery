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
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="customerid",unique=true,insertable=false, updatable=false,nullable = false)
    private Integer customerid;
    @Column (name="username")
    private String username;
    @Column (name="password",nullable = false)
    private String password;


    @OneToOne()
    @JoinColumn(name = "customerid")
    @MapsId
    private CustomerEntity customerEntity;

    @OneToMany (mappedBy = "customersCredentialsEntity",fetch = FetchType.LAZY,cascade =  CascadeType.ALL)
    private List<AuthGroupEntity> authGroupEntityList;

    public CustomersCredentialsEntity() {
    }
}
