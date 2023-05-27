package com.example.capstone.pizza_delivery_service.repositories;

import com.example.capstone.pizza_delivery_service.entity.DishesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishesRepository extends JpaRepository<DishesEntity,Integer> {

    //    List<DishesEntity> findDishesByToppingID(Long dishID);


}
