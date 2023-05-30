package com.example.capstone.pizza_delivery_service.entity;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import javassist.bytecode.ByteArray;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table (name = "foodtypes")
public class FoodTypesEntity {

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

    @NotNull
    @Column(name="description")
    private String description;

    @Column(name="image")
    private byte[] image;

    @OneToMany (mappedBy = "foodTypesEntity",fetch = FetchType.EAGER)
    private List<DishesEntity> dishesEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodTypesEntity that)) return false;
        return id.equals(that.id) && name.equals(that.name) && price.equals(that.price) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description);
    }

    @Override
    public String toString() {
        return "FoodTypesEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
