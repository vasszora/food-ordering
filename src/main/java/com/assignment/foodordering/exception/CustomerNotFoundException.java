package com.assignment.foodordering.exception;

public class CustomerNotFoundException extends EntityNotFoundException {
    public CustomerNotFoundException(Integer id) {
        super("Customer", id);
    }
}