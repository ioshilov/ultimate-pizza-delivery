package com.example.capstone.pizza_delivery_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
        private String deliveryName;
    @Column(name="deliverymobile")
    @NotNull
        private String deliveryMobile;
    @Column(name="deliveryaddress")
    @NotNull
        private String deliveryAddress;
    @Column(name="specialcomments")
        private String deliveryComments;
    @Column(name="deliveryprice")
    @NotNull
        private BigDecimal deliveryPrice;

    }
