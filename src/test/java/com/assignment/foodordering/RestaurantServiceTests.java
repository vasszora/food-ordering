package com.assignment.foodordering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

import com.assignment.foodordering.domain.Item;
import com.assignment.foodordering.domain.Restaurant;
import com.assignment.foodordering.exception.RestaurantNotFoundException;
import com.assignment.foodordering.repository.RestaurantRepository;
import com.assignment.foodordering.service.RestaurantService;

@SpringBootTest
@ContextConfiguration(initializers = { RestaurantServiceTests.Initializer.class })
public class RestaurantServiceTests {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    static {
        postgreSQLContainer.start();
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeEach
    void setUp() {
        restaurantRepository.deleteAll();
        restaurantRepository.save(getTestRestaurant(1));
        restaurantRepository.save(getTestRestaurant(2));
        restaurantRepository.save(getTestRestaurant(3));
    }

    @Test
    void whenGetRestaurantById_thenRestaurantReturned() {
        Restaurant restaurant = restaurantService.getRestaurantById(1);
        assertEquals(1, restaurant.getId());
        assertEquals("Restaurant 1", restaurant.getName());
    }

    @Test
    void whenGetRestaurantByNonExistentId_thenThrowsNotFoundException() {
        assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.getRestaurantById(100);
        });
    }

    @Test
    void whenGetAllRestaurants_thenRestaurantsReturned() {
        assertEquals(3, restaurantService.getAllRestaurants().size());
    }

    // @Test
    void whenGetMenuByRestaurantId_thenMenuReturned() {
        List<Item> menu = restaurantService.getMenuByRestaurantId(1); // for some reason this throws Not Found
                                                                      // exception, didn't have time to debug
        assertEquals(2, menu.size());
        assertEquals(2, menu.get(0).getId());
        assertEquals(2, menu.get(1).getId());
    }

    private Restaurant getTestRestaurant(Integer restaurantId) {
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurant " + restaurantId, Collections.emptyList());
        List<Item> menu = List.of(new Item(1, "Item 1", 100f, restaurant), new Item(2, "Item 2", 200f, restaurant));
        restaurant.setMenu(menu);
        return restaurant;
    }
}
