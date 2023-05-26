package com.example.capstone.pizza_delivery_service.entity;

import com.example.capstone.pizza_delivery_service.model.OrderCart;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "orderdetails")
public class OrderDetailsEntity {

        @Id
        @Column(name="id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer orderDetailsID;


    @Column(name="deliveryname")
        private String deliveryName;
    @Column(name="deliverymobile")
        private String deliveryMobile;
    @Column(name="deliveryaddress")
        private String deliveryAddress;
    @Column(name="specialcomments")
        private String deliveryComments;
    @Column(name="deliveryprice")
        private BigDecimal deliveryPrice;

    }
