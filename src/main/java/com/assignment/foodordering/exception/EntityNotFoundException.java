package com.assignment.foodordering.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not found")
public class EntityNotFoundException extends RuntimeException {
    private final String entityType;
    private final Integer entityId;

    public EntityNotFoundException(String entityType, Integer entityId) {
        super(entityType + " with id " + entityId + " not found");
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public Integer getEntityId() {
        return entityId;
    }

}
