package com.online.bus.ticket.reservation.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class BusInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long busInventoryId;
    private long busRouteNumber;
    private int totalSeats;
    private int availableSeats;
    @CreationTimestamp
    private LocalDateTime createdDateTime;
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;
}
