package com.correios.catalog.exceptions.problems;

public class EntityInUseException extends EntityException {
    private static final long serialVersionUID = 1L;

    public EntityInUseException(String message) {
        super(message);
    }
}