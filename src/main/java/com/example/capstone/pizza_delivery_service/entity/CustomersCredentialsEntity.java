package com.example.capstone.pizza_delivery_service.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min=4, max=20)
    @NotNull
    private String username;
    @Column (name="password",nullable = false)
    @Size(min=8, max=100)
    @NotNull
    private String password;


    @OneToOne()
    @JoinColumn(name = "customerid")
    @MapsId
    private CustomerEntity customerEntity;

    @OneToMany (fetch = FetchType.EAGER,cascade =  CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "customerscredentialsid")
    private List<AuthGroupEntity> authGroupEntityList;

    public CustomersCredentialsEntity() {
    }

    public CustomersCredentialsEntity(Integer id, Integer customerid, String username, String password) {
        this.id = id;
        this.customerid = customerid;
        this.username = username;
        this.password = password;
    }
}
