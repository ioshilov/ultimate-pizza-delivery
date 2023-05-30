package com.example.capstone.pizza_delivery_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Component
@SessionScope

public class OrderCart {
    private boolean isEmpty=true;

    private BigDecimal sum;
    private List<Dishes> dishesList=new ArrayList<>();
    public List<Dishes> getDishesList() {
        return dishesList;
    }
    public void addDishes (Dishes dishes){
        dishesList.add(dishes);
        sum=dishesList.stream().map(Dishes::getSum).reduce(BigDecimal.ZERO, BigDecimal::add);
        checkIfEmpty();
    }

    public void deleteDishes(int ID){
        dishesList.remove(ID);
        checkIfEmpty();
        if (dishesList.isEmpty()) {sum=BigDecimal.ZERO;}
            else {sum=dishesList.stream().map(Dishes::getSum).reduce(BigDecimal.ZERO, BigDecimal::add);}
    }

    @Override
    public String toString() {
        return "OrderCart{" +
                "dishesList=" + dishesList +
                '}';
    }

    public void checkIfEmpty() {
        setEmpty(dishesList.isEmpty());
    }
}
