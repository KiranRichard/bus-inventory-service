package com.online.bus.ticket.reservation.inventory.request;

import lombok.Data;

@Data
public class InventoryCancellationRequest {
    private long busRouteNum;
    private int noOfSeatsBooked;
}
