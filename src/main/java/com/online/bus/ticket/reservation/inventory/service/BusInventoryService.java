package com.online.bus.ticket.reservation.inventory.service;

import com.online.bus.ticket.reservation.inventory.exception.BusInventoryException;
import com.online.bus.ticket.reservation.inventory.model.BusInventory;
import com.online.bus.ticket.reservation.inventory.repository.BusInventoryRepository;
import com.online.bus.ticket.reservation.inventory.request.BusInventoryRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class BusInventoryService {

    private final BusInventoryRepository busInventoryRepository;

    public BusInventory createBusInventory(BusInventoryRequest busInventoryRequest){
        BusInventory busInventory = new BusInventory();
        busInventory.setBusRouteNumber(busInventoryRequest.getBusRouteNumber());
        busInventory.setTotalSeats(busInventoryRequest.getTotalSeats());
        busInventory.setAvailableSeats(busInventoryRequest.getAvailableSeats());
        return busInventoryRepository.save(busInventory);
    }

    public BusInventory getBusInventory(long busInventoryId) {
        BusInventory busInventory = busInventoryRepository.findById(busInventoryId).orElse(null);
        if (Objects.isNull(busInventory)){
            throw new BusInventoryException("Bus Inventory not present");
        }
        return busInventory;
    }

    public BusInventory editBusInventory(BusInventoryRequest busInventoryRequest, long busInventoryId) {
        BusInventory busInventory = busInventoryRepository.findById(busInventoryId).orElse(null);
        if (Objects.isNull(busInventory)){
            throw new BusInventoryException("Bus Inventory is not present and unable to update");
        }
        busInventory.setBusRouteNumber(busInventoryRequest.getBusRouteNumber());
        busInventory.setTotalSeats(busInventoryRequest.getTotalSeats());
        busInventory.setAvailableSeats(busInventoryRequest.getAvailableSeats());
        return busInventoryRepository.save(busInventory);
    }

    public void deleteBusInventory(long busInventoryId) {
        if (busInventoryRepository.findById(busInventoryId).isEmpty()) {
            throw new BusInventoryException("Bus Inventory is not present and unable to delete");
        }
        busInventoryRepository.deleteById(busInventoryId);
    }

    public List<BusInventory> getBusInventories() {
        return (List<BusInventory>) busInventoryRepository.findAll();
    }

    public BusInventory getBusInventoryByBusRouteNumber(long busRouteNumber) {
        BusInventory busInventory = busInventoryRepository.findByBusRouteNumber(busRouteNumber).orElse(null);
        if (Objects.isNull(busInventory)){
            throw new BusInventoryException("Bus Inventory not present");
        }
        return busInventory;
    }
}
