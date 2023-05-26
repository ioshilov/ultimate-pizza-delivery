package com.example.capstone.pizza_delivery_service.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "dishes")
public class DishesEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dishesID;

    @NotNull
    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name = "foodtypeid")
    private FoodTypesEntity foodTypesEntity;


    @ManyToMany
    @JoinTable(
            name = "toppings_dishes",
            joinColumns = @JoinColumn(name = "toppingid"),
            inverseJoinColumns = @JoinColumn(name = "dishid"))
    List<ToppingsEntity> toppingsEntitySet=new ArrayList<>();

    public void addToppings (ToppingsEntity toppingsEntity){
        toppingsEntitySet.add(toppingsEntity);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DishesEntity that)) return false;
        return dishesID.equals(that.dishesID) && foodTypesEntity.equals(that.foodTypesEntity) && toppingsEntitySet.equals(that.toppingsEntitySet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishesID, foodTypesEntity, toppingsEntitySet);
    }
}
