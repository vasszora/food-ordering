package com.assignment.foodordering.service;

import org.springframework.stereotype.Service;

import com.assignment.foodordering.domain.Item;
import com.assignment.foodordering.exception.ItemNotFoundException;
import com.assignment.foodordering.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item getItemById(Integer id) {
        return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }
}
