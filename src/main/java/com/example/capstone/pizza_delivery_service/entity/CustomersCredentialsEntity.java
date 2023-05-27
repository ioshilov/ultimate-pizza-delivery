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
    @Column (name="username")
    private String username;
    @Column (name="password")
    private String password;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid")
    private CustomerEntity customerEntity;

    @OneToMany (mappedBy = "customersCredentialsEntity",fetch = FetchType.EAGER)
    private List<AuthGroupEntity> authGroupEntityList;

    public CustomersCredentialsEntity() {
    }
}
