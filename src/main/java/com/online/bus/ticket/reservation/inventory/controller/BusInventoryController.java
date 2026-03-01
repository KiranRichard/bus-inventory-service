package com.online.bus.ticket.reservation.inventory.controller;

import com.online.bus.ticket.reservation.inventory.model.BusInventory;
import com.online.bus.ticket.reservation.inventory.request.BusInventoryRequest;
import com.online.bus.ticket.reservation.inventory.service.BusInventoryService;
import com.online.bus.ticket.reservation.inventory.validator.BusInventoryValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inventory/buses")
@AllArgsConstructor
public class BusInventoryController {

    private final BusInventoryValidator busInventoryValidator;
    private final BusInventoryService busInventoryService;

    @PostMapping
    public BusInventory createBusInventory(@RequestBody BusInventoryRequest busInventoryRequest) {
        log.info("Inside BusInventoryController createBusInventory Method");
        busInventoryValidator.validateBusInventory(busInventoryRequest);
        return busInventoryService.createBusInventory(busInventoryRequest);
    }

    @GetMapping("/{busInventoryId}")
    public BusInventory getBusInventory(@PathVariable("busInventoryId") long busInventoryId) {
        log.info("Inside BusInventoryController getBusInventory Method with busInventoryId: {}", busInventoryId);
        busInventoryValidator.validateBusInventoryId(busInventoryId);
        return busInventoryService.getBusInventory(busInventoryId);
    }

    @GetMapping()
    public List<BusInventory> getBusInventories() {
        log.info("Inside BusInventoryController getBusInventories Method");
        return busInventoryService.getBusInventories();
    }

    @PutMapping("/{busInventoryId}")
    public BusInventory editBusInventory(@RequestBody BusInventoryRequest busInventoryRequest, @PathVariable("busInventoryId") long busInventoryId) {
        log.info("Inside BusInventoryController editBusInventory Method with busInventoryId: {}", busInventoryId);
        busInventoryValidator.validateBusInventoryId(busInventoryId);
        busInventoryValidator.validateBusInventory(busInventoryRequest);
        return busInventoryService.editBusInventory(busInventoryRequest, busInventoryId);
    }

    @DeleteMapping("/{busInventoryId}")
    public void deleteBusInventory(@PathVariable("busInventoryId") long busInventoryId) {
        log.info("Inside BusInventoryController deleteBusInventory Method with busInventoryId: {}", busInventoryId);
        busInventoryValidator.validateBusInventoryId(busInventoryId);
        busInventoryService.deleteBusInventory(busInventoryId);
    }

    @GetMapping("/busRoute/{busRouteNumber}")
    public BusInventory getBusInventoryByBusRouteNumber(@PathVariable("busRouteNumber") long busRouteNumber) {
        log.info("Inside BusInventoryController getBusInventoryByBusRouteNumber Method with busRouteNumber: {}", busRouteNumber);
        busInventoryValidator.validateBusRouteNumber(busRouteNumber);
        return busInventoryService.getBusInventoryByBusRouteNumber(busRouteNumber);
    }
}
