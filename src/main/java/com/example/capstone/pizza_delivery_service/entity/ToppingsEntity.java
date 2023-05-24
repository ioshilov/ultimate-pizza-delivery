package com.example.capstone.pizza_delivery_service.entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Data
@Table (name = "toppings")
public class ToppingsEntity {

    @Id
    @Column(name="TOPPINGID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer toppingsID;

    @NotNull
    @Column(name="NAME")
    private String name;

    @NotNull
    @Column(name="PRICE")
    private BigDecimal price;


}
