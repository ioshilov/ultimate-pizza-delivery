package com.example.capstone.pizza_delivery_service.repositories;

import com.example.capstone.pizza_delivery_service.entity.ToppingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToppingsRepository extends JpaRepository<ToppingsEntity,Integer> {

    ToppingsEntity findByName(String name);

//    List<ToppingsEntity> findToppingsByDishesID(Long dishID);
}
