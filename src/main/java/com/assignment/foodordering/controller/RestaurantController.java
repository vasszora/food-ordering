package com.assignment.foodordering.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.foodordering.domain.Item;
import com.assignment.foodordering.domain.Restaurant;
import com.assignment.foodordering.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Restaurant getAppointmentById(@PathVariable("id") Integer id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping(value = "/{id}/menu", produces = APPLICATION_JSON_VALUE)
    public List<Item> getMenuByRestaurantId(@PathVariable("id") Integer id) {
        return restaurantService.getMenuByRestaurantId(id);
    }
}
