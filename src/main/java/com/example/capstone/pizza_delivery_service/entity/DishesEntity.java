package com.example.capstone.pizza_delivery_service.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "dishes")
public class DishesEntity {
    @Id
    @Column(name="DISHESID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer DishesID;

    @NotNull
    @ManyToOne
    private FoodTypesEntity FoodTypesEntity;


    @ManyToOne
    private ToppingsEntity ToppingsEntity;


}
