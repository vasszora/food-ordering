package com.assignment.foodordering.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.foodordering.domain.Order;
import com.assignment.foodordering.domain.OrderSaveRequest;
import com.assignment.foodordering.domain.OrderUpdateRequest;
import com.assignment.foodordering.exception.EntityNotFoundException;
import com.assignment.foodordering.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<Order> getOrdersByRestaurantId(@RequestParam("restaurantId") Integer restaurantId) {
        return orderService.getOrdersByRestaurantId(restaurantId);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Order getOrderById(@PathVariable("id") Integer id) {
        return orderService.getOrderById(id);
    }

    @PatchMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Order updateOrder(@PathVariable("id") Integer id, @RequestBody OrderUpdateRequest updateRequest) {
        return orderService.updateOrder(id, updateRequest);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Order createOrder(@RequestBody OrderSaveRequest order) {
        return orderService.createOrder(order);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementFoundException(
            EntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
