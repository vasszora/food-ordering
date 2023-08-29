package com.assignment.foodordering.service;

import org.springframework.stereotype.Service;

import com.assignment.foodordering.domain.Customer;
import com.assignment.foodordering.exception.CustomerNotFoundException;
import com.assignment.foodordering.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
