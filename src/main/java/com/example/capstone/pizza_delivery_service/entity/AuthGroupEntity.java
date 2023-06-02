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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name="authgroup",nullable = false)
    private String authgroup;

//    @Column (name="customerscredentialsid",nullable = false,insertable=false, updatable=false)
//    private Integer customerscredentialsid;
//
//
//    @ManyToOne (optional=false, cascade=CascadeType.ALL)
//    @JoinColumn(name = "customerscredentialsid")
//    private CustomersCredentialsEntity customersCredentialsEntity;


    public AuthGroupEntity() {
    }

    public AuthGroupEntity(Integer id, String authgroup) {
        this.id = id;
        this.authgroup = authgroup;
    }
}
