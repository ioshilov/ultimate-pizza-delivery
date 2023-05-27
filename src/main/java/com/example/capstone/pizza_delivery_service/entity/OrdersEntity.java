package com.example.capstone.pizza_delivery_service.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrdersEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "ordersEntity")
    private OrderDetailsEntity orderDetailsEntity;


    @Column(name="sum")
    private BigDecimal sum;
    @Column(name="date")
    private LocalDate localDate;





}
