package com.example.capstone.pizza_delivery_service.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="authusergroup")
public class AuthGroupEntity {
    @Id
    @Column(name="id")
    private Integer id;
    @Column (name="usernameauth",nullable = false)
    private String usernameauth;
    @Column (name="authgroup",nullable = false)
    private String authgroup;

    @NotNull
    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name = "customerscredentialsid")
    private CustomersCredentialsEntity customersCredentialsEntity;

}
