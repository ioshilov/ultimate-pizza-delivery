package com.example.capstone.pizza_delivery_service.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            joinColumns = @JoinColumn(name = "dishid"),
            inverseJoinColumns = @JoinColumn(name = "toppingid"))
    List<ToppingsEntity> toppingsEntitySet=new ArrayList<>();


    @ManyToMany(mappedBy = "dishesEntityList")
    private List<OrderCartEntity> orderCartEntities=new ArrayList<>();

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

    @Override
    public String toString() {
        if (toppingsEntitySet.isEmpty()){return foodTypesEntity.getName();}

        return foodTypesEntity.getName() +" + toppings:"+ toppingsEntitySet.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    public DishesEntity() {
    }

    public DishesEntity(Integer dishesID, FoodTypesEntity foodTypesEntity, List<ToppingsEntity> toppingsEntitySet) {
        this.dishesID = dishesID;
        this.foodTypesEntity = foodTypesEntity;
        this.toppingsEntitySet = toppingsEntitySet;
    }
}


