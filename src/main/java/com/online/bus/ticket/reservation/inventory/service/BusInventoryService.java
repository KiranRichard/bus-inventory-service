package com.online.bus.ticket.reservation.inventory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.bus.ticket.reservation.inventory.exception.BusInventoryException;
import com.online.bus.ticket.reservation.inventory.kafka.ProducerService;
import com.online.bus.ticket.reservation.inventory.model.BusInventory;
import com.online.bus.ticket.reservation.inventory.repository.BusInventoryRepository;
import com.online.bus.ticket.reservation.inventory.request.BookingUpdateRequest;
import com.online.bus.ticket.reservation.inventory.request.BusInventoryRequest;
import com.online.bus.ticket.reservation.inventory.request.InventoryUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BusInventoryService {

    @Autowired
    private BusInventoryRepository busInventoryRepository;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private ObjectMapper objectMapper;

    public BusInventory createBusInventory(BusInventoryRequest busInventoryRequest){
        BusInventory busInventory = new BusInventory();
        busInventory.setBusRouteNumber(busInventoryRequest.getBusRouteNumber());
        busInventory.setTotalSeats(busInventoryRequest.getTotalSeats());
        busInventory.setAvailableSeats(busInventoryRequest.getAvailableSeats());
        busInventory.setPrice(busInventoryRequest.getPrice());
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
        busInventory.setPrice(busInventoryRequest.getPrice());
        return busInventoryRepository.save(busInventory);
    }

    public BusInventory editBusInventoryByBusRouteNumber(BusInventoryRequest busInventoryRequest, long busRouteNumber) {
        BusInventory busInventory = busInventoryRepository.findByBusRouteNumber(busRouteNumber).orElse(null);
        if (Objects.isNull(busInventory)){
            throw new BusInventoryException("Bus Inventory is not present and unable to update");
        }
        busInventory.setBusRouteNumber(busInventoryRequest.getBusRouteNumber());
        busInventory.setTotalSeats(busInventoryRequest.getTotalSeats());
        busInventory.setAvailableSeats(busInventoryRequest.getAvailableSeats());
        busInventory.setPrice(busInventoryRequest.getPrice());
        return busInventoryRepository.save(busInventory);
    }

    public void deleteBusInventory(long busInventoryId) {
        if (busInventoryRepository.findById(busInventoryId).isEmpty()) {
            throw new BusInventoryException("Bus Inventory is not present and unable to delete");
        }
        busInventoryRepository.deleteById(busInventoryId);
    }

    public void deleteBusInventoryByBusRouteNumber(long busRouteNumber) {
        BusInventory busInventory = busInventoryRepository.findByBusRouteNumber(busRouteNumber).orElse(null);
        if (Objects.isNull(busInventory)) {
            throw new BusInventoryException("Bus Inventory is not present and unable to delete");
        }
        busInventoryRepository.deleteById(busInventory.getBusInventoryId());
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

    public BusInventory editAvailableSeatsByBusRouteNumber(InventoryUpdateRequest inventoryUpdateRequest) throws JsonProcessingException {
        BusInventory busInventory = busInventoryRepository.findByBusRouteNumber(inventoryUpdateRequest.getBusRouteNum()).orElse(null);
        if (Objects.isNull(busInventory)){
            throw new BusInventoryException("Bus Inventory is not present and unable to update");
        }

        busInventory.setAvailableSeats(busInventory.getAvailableSeats() - inventoryUpdateRequest.getNoOfSeatsBooked());
        BusInventory savedBusInventory = busInventoryRepository.save(busInventory);

        BookingUpdateRequest bookingUpdateRequest = new BookingUpdateRequest();
        bookingUpdateRequest.setBookingId(inventoryUpdateRequest.getBookingId());
        String jsonMessage = objectMapper.writeValueAsString(bookingUpdateRequest);
        producerService.sendMessage(jsonMessage);
        return savedBusInventory;
    }

    public BusInventory cancelAvailableSeatsByBusRouteNumber(InventoryUpdateRequest inventoryUpdateRequest) throws JsonProcessingException {
        BusInventory busInventory = busInventoryRepository.findByBusRouteNumber(inventoryUpdateRequest.getBusRouteNum()).orElse(null);
        if (Objects.isNull(busInventory)){
            throw new BusInventoryException("Bus Inventory is not present and unable to update");
        }

        busInventory.setAvailableSeats(busInventory.getAvailableSeats() + inventoryUpdateRequest.getNoOfSeatsBooked());
        BusInventory savedBusInventory = busInventoryRepository.save(busInventory);

        BookingUpdateRequest bookingUpdateRequest = new BookingUpdateRequest();
        bookingUpdateRequest.setBookingId(inventoryUpdateRequest.getBookingId());
        String jsonMessage = objectMapper.writeValueAsString(bookingUpdateRequest);
        producerService.sendCancelMessage(jsonMessage);
        return savedBusInventory;
    }
}
