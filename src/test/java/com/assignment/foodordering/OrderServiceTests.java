package com.assignment.foodordering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.assignment.foodordering.domain.Order;
import com.assignment.foodordering.domain.OrderSaveRequest;
import com.assignment.foodordering.domain.OrderStatus;
import com.assignment.foodordering.domain.OrderUpdateRequest;
import com.assignment.foodordering.domain.OrderedItemSaveRequest;
import com.assignment.foodordering.exception.CustomerNotFoundException;
import com.assignment.foodordering.exception.ItemNotFoundException;
import com.assignment.foodordering.exception.OrderNotFoundException;
import com.assignment.foodordering.exception.RestaurantNotFoundException;
import com.assignment.foodordering.service.OrderService;

@SpringBootTest
public class OrderServiceTests {
    @Autowired
    private OrderService orderService;

    @Test
    void whenGetOrdersByRestaurantId_thenOrdersReturned() {
        List<Order> orders = orderService.getOrdersByRestaurantId(3);
        assertEquals(1, orders.size());
    }

    @Test
    void whenGetOrderById_thenOrderReturned() {
        Order order = orderService.getOrderById(1);
        assertEquals(1, order.getId());
    }

    @Test
    void whenGetOrderByNonExistentId_thenThrowsNotFoundException() {
        assertThrows(OrderNotFoundException.class, () -> {
            orderService.getOrderById(100);
        });
    }

    @Test
    void whenCreateOrder_thenOrderCreated() {
        OrderSaveRequest orderSaveRequest = getTestOrderSaveRequest(1, 2, List.of(1, 2));
        Order order = orderService.createOrder(orderSaveRequest);
        assertEquals(1, order.getCustomer().getId());
        assertEquals(2, order.getRestaurant().getId());
        assertEquals(2, order.getItems().size());
    }

    @Test
    void whenCreateOrderWithNonExistentRestaurantId_thenThrowsNotFoundException() {
        OrderSaveRequest orderSaveRequest = getTestOrderSaveRequest(1, 100, List.of(1, 2));
        assertThrows(RestaurantNotFoundException.class, () -> {
            orderService.createOrder(orderSaveRequest);
        });
    }

    @Test
    void whenCreateOrderWithNonExistentCustomerId_thenThrowsNotFoundException() {
        OrderSaveRequest orderSaveRequest = getTestOrderSaveRequest(100, 1, List.of(1, 2));
        assertThrows(CustomerNotFoundException.class, () -> {
            orderService.createOrder(orderSaveRequest);
        });
    }

    @Test
    void whenCreateOrderWithNonExistentItemId_thenThrowsNotFoundException() {
        OrderSaveRequest orderSaveRequest = getTestOrderSaveRequest(1, 1, List.of(100));
        assertThrows(ItemNotFoundException.class, () -> {
            orderService.createOrder(orderSaveRequest);
        });
    }

    @Test
    void whenUpdateOrder_thenOrderUpdated() {
        OrderUpdateRequest updateRequest = new OrderUpdateRequest(OrderStatus.PREPARING);
        Order order = orderService.updateOrder(1, updateRequest);
        assertEquals(1, order.getId());
        assertEquals(OrderStatus.PREPARING, order.getStatus());
    }

    @Test
    void whenUpdateOrderWithNonExistentId_thenThrowsNotFoundException() {
        OrderUpdateRequest updateRequest = new OrderUpdateRequest(OrderStatus.PREPARING);
        assertThrows(OrderNotFoundException.class, () -> {
            orderService.updateOrder(100, updateRequest);
        });
    }

    private OrderSaveRequest getTestOrderSaveRequest(Integer customerId, Integer restaurantId, List<Integer> itemIds) {
        OrderSaveRequest orderSaveRequest = new OrderSaveRequest();
        orderSaveRequest.setCustomerId(customerId);
        orderSaveRequest.setRestaurantId(restaurantId);

        orderSaveRequest.setItems(getTestOrderedItems(itemIds));
        return orderSaveRequest;
    }

    private List<OrderedItemSaveRequest> getTestOrderedItems(List<Integer> itemIds) {
        List<OrderedItemSaveRequest> orderedItems = new ArrayList<>();
        for (Integer itemId : itemIds) {
            OrderedItemSaveRequest orderedItem = new OrderedItemSaveRequest();
            orderedItem.setItemId(itemId);
            orderedItem.setQuantity(1);
            orderedItem.setSpecialInstructions("No onions");
            orderedItems.add(orderedItem);
        }
        return orderedItems;
    }
}
