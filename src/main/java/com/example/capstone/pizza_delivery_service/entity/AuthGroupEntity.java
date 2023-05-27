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
    @Column (name="username",insertable=false, updatable=false)
    private String username;
    @Column (name="authgroup")
    private String authgroup;

    @NotNull
    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name = "username")
    private CustomersCredentialsEntity customersCredentialsEntity;

}
