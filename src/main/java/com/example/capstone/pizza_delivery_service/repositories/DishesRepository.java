package com.example.capstone.pizza_delivery_service.repositories;

import com.example.capstone.pizza_delivery_service.entity.DishesEntity;
import com.example.capstone.pizza_delivery_service.entity.ToppingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishesRepository extends JpaRepository<DishesEntity,Integer> {

    //    List<DishesEntity> findDishesByToppingID(Long dishID);


}
