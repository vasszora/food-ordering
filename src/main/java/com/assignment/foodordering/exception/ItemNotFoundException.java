package com.assignment.foodordering.exception;

public class ItemNotFoundException extends EntityNotFoundException {
    public ItemNotFoundException(Integer id) {
        super("Item", id);
    }
}
