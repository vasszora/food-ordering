package com.assignment.foodordering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.foodordering.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findAll();

    Optional<Restaurant> findById(Integer id);

    Restaurant save(Restaurant restaurant);
}
