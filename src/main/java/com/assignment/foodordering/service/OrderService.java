package com.assignment.foodordering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.assignment.foodordering.domain.Customer;
import com.assignment.foodordering.domain.Item;
import com.assignment.foodordering.domain.Order;
import com.assignment.foodordering.domain.OrderSaveRequest;
import com.assignment.foodordering.domain.OrderStatus;
import com.assignment.foodordering.domain.OrderUpdateRequest;
import com.assignment.foodordering.domain.OrderedItem;
import com.assignment.foodordering.domain.OrderedItemSaveRequest;
import com.assignment.foodordering.domain.Restaurant;
import com.assignment.foodordering.repository.CustomerRepository;
import com.assignment.foodordering.repository.ItemRepository;
import com.assignment.foodordering.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantService restaurantService;
    private final ItemRepository itemRepository;

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            setMenu(order);
        }
        return orders;
    }

    public Optional<Order> getOrderById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            setMenu(order.get());
        }
        return order;
    }

    private void setMenu(Order order) {
        Hibernate.initialize(order.getItems());
        order.getItems().forEach(orderedItem -> {
            Hibernate.initialize(orderedItem.getItem());
        });
    }

    public Order updateOrder(Integer id, OrderUpdateRequest updateRequest) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            order.get().setStatus(updateRequest.getStatus());
            return orderRepository.save(order.get());
        }
        return null;
    }

    public Order createOrder(OrderSaveRequest orderSaveRequest) {
        Order newOrder = new Order();
        Optional<Customer> customer = customerRepository.findById(orderSaveRequest.getCustomerId());
        if (customer.isEmpty()) {
            System.out.println("Customer not found");
            // TODO add error if customer not found
        } else {
            newOrder.setCustomer(customer.get());
        }

        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(orderSaveRequest.getRestaurantId());
        if (restaurant.isEmpty()) {
            System.out.println("Restaurant not found");
            // TODO add error if restaurant not found
        } else {
            newOrder.setRestaurant(restaurant.get());
        }

        List<OrderedItem> orderedItems = new ArrayList<>();
        for (OrderedItemSaveRequest orderedItemRequest : orderSaveRequest.getItems()) {
            Optional<Item> item = itemRepository.findById(orderedItemRequest.getItemId());
            if (item.isEmpty()) {
                System.out.println("Item not found");
                // TODO add error if item not found
            } else {
                orderedItems.add(
                        new OrderedItem(newOrder,
                                item.get(),
                                orderedItemRequest.getQuantity(),
                                orderedItemRequest.getSpecialInstructions()));
            }
        }
        newOrder.setItems(orderedItems);

        newOrder.setStatus(OrderStatus.RECEIVED);
        return orderRepository.save(newOrder);
    }
}
