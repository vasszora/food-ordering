package com.assignment.foodordering.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
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
import com.assignment.foodordering.exception.OrderNotFoundException;
import com.assignment.foodordering.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final ItemService itemService;

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            setMenu(order);
        }
        return orders;
    }

    public Order getOrderById(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        setMenu(order);
        return order;
    }

    public Order updateOrder(Integer id, OrderUpdateRequest updateRequest) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        order.setStatus(updateRequest.getStatus());
        return orderRepository.save(order);
    }

    public Order createOrder(OrderSaveRequest orderSaveRequest) {
        Order newOrder = new Order();
        Customer customer = customerService.getCustomerById(orderSaveRequest.getCustomerId());
        newOrder.setCustomer(customer);

        Restaurant restaurant = restaurantService.getRestaurantById(orderSaveRequest.getRestaurantId());
        newOrder.setRestaurant(restaurant);

        List<OrderedItem> orderedItems = new ArrayList<>();
        for (OrderedItemSaveRequest orderedItemRequest : orderSaveRequest.getItems()) {
            Item item = itemService.getItemById(orderedItemRequest.getItemId());

            orderedItems.add(
                    new OrderedItem(newOrder,
                            item,
                            orderedItemRequest.getQuantity(),
                            orderedItemRequest.getSpecialInstructions()));
        }
        newOrder.setItems(orderedItems);

        newOrder.setStatus(OrderStatus.RECEIVED);
        return orderRepository.save(newOrder);
    }

    private void setMenu(Order order) {
        Hibernate.initialize(order.getItems());
        order.getItems().forEach(orderedItem -> Hibernate.initialize(orderedItem.getItem()));
    }
}
