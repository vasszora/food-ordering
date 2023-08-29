package com.assignment.foodordering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.foodordering.domain.Order;
import com.assignment.foodordering.domain.Restaurant;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAll();

    Optional<Order> findById(Integer id);

    List<Order> findByRestaurant(Restaurant restaurant);

    // TODO path with status
}
