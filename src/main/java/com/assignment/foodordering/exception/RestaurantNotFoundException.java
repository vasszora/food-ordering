package com.assignment.foodordering.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {
    public RestaurantNotFoundException(Integer id) {
        super("Restaurant", id);
    }
}
