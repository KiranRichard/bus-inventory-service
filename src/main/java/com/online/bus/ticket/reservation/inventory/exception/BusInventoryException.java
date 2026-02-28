package com.online.bus.ticket.reservation.inventory.exception;

public class BusInventoryException extends RuntimeException {

    private String errorMessage;

    public BusInventoryException() {
        super();
    }

    public BusInventoryException(String errorMessage) {
        super(errorMessage);
    }
}
