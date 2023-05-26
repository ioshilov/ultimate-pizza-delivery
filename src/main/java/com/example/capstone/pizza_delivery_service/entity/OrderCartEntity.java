package com.example.capstone.pizza_delivery_service.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "ordercart")
public class OrderCartEntity {

    @Id
    @Column(name="id")
    private Integer orderCartID;


    @ManyToOne
    private DishesEntity dishesEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private OrderDetailsEntity orderDetailsEntity;

}
