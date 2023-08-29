package com.assignment.foodordering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.foodordering.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAll();

    Optional<Item> findById(Integer id);
}
