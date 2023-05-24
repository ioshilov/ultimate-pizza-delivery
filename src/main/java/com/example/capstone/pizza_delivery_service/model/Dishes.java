package com.example.capstone.pizza_delivery_service.model;

import lombok.Data;

@Data
public class Dishes {

//    private Integer ID;
    private Integer FOODTYPEID;
    private Boolean jalapeno;
    private Boolean pepper;
    private Boolean cheese;

    public Dishes(Integer FOODTYPEID, boolean jalapeno, boolean pepper, boolean cheese) {
        this.FOODTYPEID = FOODTYPEID;
        this.jalapeno = jalapeno;
        this.pepper = pepper;
        this.cheese = cheese;
    }
}
