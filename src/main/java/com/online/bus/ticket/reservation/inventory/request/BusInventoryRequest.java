package com.online.bus.ticket.reservation.inventory.request;

import lombok.Data;

@Data
public class BusInventoryRequest {

    private long busRouteNumber;
    private int totalSeats;
    private int availableSeats;
}
