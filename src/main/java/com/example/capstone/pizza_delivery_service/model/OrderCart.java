package com.example.capstone.pizza_delivery_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Component
@SessionScope

public class OrderCart {


   private List<Dishes> dishesList=new ArrayList<>();

    public List<Dishes> getDishesList() {
        return dishesList;
    }

    public void addDishes (Dishes dishes){
        dishesList.add(dishes);
    }

    public void deleteDishes(int ID){
        dishesList.remove(ID);
    }

    @Override
    public String toString() {
        return "OrderCart{" +
                "dishesList=" + dishesList +
                '}';
    }
}
