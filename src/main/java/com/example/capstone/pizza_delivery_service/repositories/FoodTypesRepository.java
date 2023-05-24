package com.example.capstone.pizza_delivery_service.repositories;

import com.example.capstone.pizza_delivery_service.entity.FoodTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTypesRepository extends JpaRepository<FoodTypesEntity,Integer> {
}
