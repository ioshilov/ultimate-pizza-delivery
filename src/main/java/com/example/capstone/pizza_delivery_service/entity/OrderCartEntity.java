package com.example.capstone.pizza_delivery_service.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Table(name = "ordercart")
public class OrderCartEntity {

    @Id
    @Column(name="ORDERCARTID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderCartID;


    @ManyToOne
    private DishesEntity dishesEntity;


}
