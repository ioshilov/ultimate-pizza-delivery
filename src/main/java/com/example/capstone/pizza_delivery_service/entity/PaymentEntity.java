package com.example.capstone.pizza_delivery_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="orderid",unique=true,insertable=false, updatable=false,nullable = false)
    private Integer orderid;

    @Column (name="paymentmethod")
    @Size(max=20)
    @NotNull
    private String paymentmethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderid", referencedColumnName = "id")
    private OrdersEntity ordersEntity;

}
