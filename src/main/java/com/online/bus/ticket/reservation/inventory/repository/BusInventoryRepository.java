package com.online.bus.ticket.reservation.inventory.repository;

import com.online.bus.ticket.reservation.inventory.model.BusInventory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BusInventoryRepository extends CrudRepository<BusInventory, Long> {

    Optional<BusInventory> findByBusRouteNumber(long busRouteNumber);
}
