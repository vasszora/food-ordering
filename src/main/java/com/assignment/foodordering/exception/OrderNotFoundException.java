package com.assignment.foodordering.exception;

public class OrderNotFoundException extends EntityNotFoundException {
    public OrderNotFoundException(Integer id) {
        super("Order", id);
    }
}
