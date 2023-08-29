package com.assignment.foodordering.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment.foodordering.domain.Restaurant;
import com.assignment.foodordering.exception.RestaurantNotFoundException;
import com.assignment.foodordering.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Integer id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
    }
}
