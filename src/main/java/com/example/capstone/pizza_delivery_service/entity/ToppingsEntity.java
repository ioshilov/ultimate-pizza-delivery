package com.example.capstone.pizza_delivery_service.entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@Table (name = "toppings")
public class ToppingsEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name="name")
    private String name;

    @NotNull
    @Column(name="price")
    private BigDecimal price;

    @ManyToMany(mappedBy = "toppingsEntitySet")
    private List<DishesEntity> dishesEntitySet=new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToppingsEntity that)) return false;
        return id.equals(that.id) && name.equals(that.name) && price.equals(that.price) && Objects.equals(dishesEntitySet, that.dishesEntitySet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, dishesEntitySet);
    }

    @Override
    public String toString() {
        return  name;
    }
}
