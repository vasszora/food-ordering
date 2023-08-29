package com.assignment.foodordering.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedItemSaveRequest {
    private Integer itemId;
    private Integer quantity;
    private String specialInstructions;
}
