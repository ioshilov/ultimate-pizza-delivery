package com.example.capstone.pizza_delivery_service.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name="customerscredentials")
public class CustomersCredentialsEntity {

    @Id
    @Column(name="CUSTOMERID")
    private Integer id;
    @Column (name="username")
    private String login;
    @Column (name="password")
    private String password;
    @Column (name="role")
    private String role;

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    private CustomerEntity customerEntity;

    public CustomersCredentialsEntity() {
    }
}
