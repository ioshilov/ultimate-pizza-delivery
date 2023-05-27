package com.example.capstone.pizza_delivery_service.entity;

import jakarta.persistence.*;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordercartid", referencedColumnName = "id")
    private OrderCartEntity orderCartEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderid", referencedColumnName = "id")
    private OrdersEntity ordersEntity;



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
