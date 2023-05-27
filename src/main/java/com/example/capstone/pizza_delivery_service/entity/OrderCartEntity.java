package com.example.capstone.pizza_delivery_service.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "ordercart")
public class OrderCartEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderCartID;


    @ManyToMany
    @JoinTable(
            name = "ordercart_dishes",
            joinColumns = @JoinColumn(name = "ordercartid"),
            inverseJoinColumns = @JoinColumn(name = "dishid"))
    private List<DishesEntity> dishesEntityList=new ArrayList<>();


    @OneToOne(mappedBy = "orderCartEntity")
    private OrderDetailsEntity orderDetailsEntity;

    public void addDishes(DishesEntity dishesEntity) {
        dishesEntityList.add(dishesEntity);
    }

}
