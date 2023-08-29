package com.assignment.foodordering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.foodordering.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAll();

    Optional<Customer> findById(Integer id);
}
