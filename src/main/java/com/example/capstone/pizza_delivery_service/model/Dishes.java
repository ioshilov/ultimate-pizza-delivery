package com.example.capstone.pizza_delivery_service.model;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Component
@SessionScope
public class Dishes {
    private final FoodTypes foodType;
   private List<Toppings> toppings;
   private BigDecimal sum;

   private String description;

    public Dishes(FoodTypes foodType) {
        this.foodType = foodType;
        updateSum();
        setDescription(toString());
    }

    public void setToppings(List<Toppings> toppings) {
        this.toppings = toppings;
        updateSum();
        setDescription(toString());
    }

    public String toString (){
        if (toppings==null) {return foodType.getName();}
        if (toppings.isEmpty()) {return foodType.getName();}
        return foodType.getName()+" | toppings: "+toppings.stream().map(Toppings::getName).collect(Collectors.joining(", "));
}

    public void updateSum(){
        BigDecimal foodTypePrice=foodType.getPrice();

        if (toppings!=null){
            BigDecimal sumToppings =toppings.stream().map(Toppings::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal result=foodTypePrice.add(sumToppings);
            setSum(result);
            }else
            {setSum(foodTypePrice);}
    }

    private void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
