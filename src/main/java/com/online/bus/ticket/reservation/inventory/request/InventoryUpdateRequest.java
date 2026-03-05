package com.online.bus.ticket.reservation.inventory.request;

import lombok.Data;

@Data
public class InventoryUpdateRequest {
    private long busRouteNum;
    private int noOfSeatsBooked;
}
