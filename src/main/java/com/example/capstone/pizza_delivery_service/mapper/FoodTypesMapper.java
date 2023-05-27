package com.example.capstone.pizza_delivery_service.mapper;

import com.example.capstone.pizza_delivery_service.entity.FoodTypesEntity;
import com.example.capstone.pizza_delivery_service.model.FoodTypes;

public class FoodTypesMapper {


  public FoodTypes mapFoodTypesEntityToModel (FoodTypesEntity foodTypesEntity){
        return new FoodTypes(foodTypesEntity.getName(), foodTypesEntity.getPrice(),foodTypesEntity.getDescription());
    }

}
