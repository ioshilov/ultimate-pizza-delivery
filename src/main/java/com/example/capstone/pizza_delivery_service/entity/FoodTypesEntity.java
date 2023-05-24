package com.example.capstone.pizza_delivery_service.entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table (name = "foodtypes")
public class FoodTypesEntity {

    @Id
    @Column(name="FOODTYPEID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer foodTypesID;

    @NotNull
    @Column(name="NAME")
    private String name;

    @NotNull
    @Column(name="PRICE")
    private BigDecimal price;

    @NotNull
    @Column(name="DESCRIPTION")
    private String description;

}
