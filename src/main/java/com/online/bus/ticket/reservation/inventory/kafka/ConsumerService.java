package com.online.bus.ticket.reservation.inventory.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.bus.ticket.reservation.inventory.model.BusInventory;
import com.online.bus.ticket.reservation.inventory.request.BusInventoryRequest;
import com.online.bus.ticket.reservation.inventory.request.InventoryUpdateRequest;
import com.online.bus.ticket.reservation.inventory.service.BusInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class ConsumerService {

    @Autowired
    BusInventoryService busInventoryService;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "admin-topic-insert", groupId = "admin-group")
    public void consumeBusRouteInsert(String message) throws JsonProcessingException {
        log.info("Received Message for Bus Route insert:{}", message);
        BusInventoryRequest busInventoryRequest =
                objectMapper.readValue(message, BusInventoryRequest.class);
        BusInventory busInventory = busInventoryService.createBusInventory(busInventoryRequest);
        if (Objects.isNull(busInventory)) {
            log.info("[Error] Message unable to process");
        }
        log.info("The message received: {} has been processed sucessfully.", message);
    }

    @KafkaListener(topics = "admin-topic-update", groupId = "admin-group")
    public void consumeBusRouteUpdate(String message) throws JsonProcessingException {
        log.info("Received Message for Bus Route update :{}", message);
        BusInventoryRequest busInventoryRequest =
                objectMapper.readValue(message, BusInventoryRequest.class);
        BusInventory busInventory = busInventoryService.editBusInventoryByBusRouteNumber(busInventoryRequest, busInventoryRequest.getBusRouteNumber());
        if (Objects.isNull(busInventory)) {
            log.info("[Error] Message unable to process");
        }
        log.info("The message received: {} has been processed sucessfully.", message);
    }

    @KafkaListener(topics = "admin-topic-delete", groupId = "admin-group")
    public void consumeBusRouteDelete(String message) throws JsonProcessingException {
        log.info("Received Message for Bus Route delete :{}", message);
        BusInventoryRequest busInventoryRequest =
                objectMapper.readValue(message, BusInventoryRequest.class);
        busInventoryService.deleteBusInventoryByBusRouteNumber(busInventoryRequest.getBusRouteNumber());
        log.info("The message received: {} has been processed sucessfully. Please validate the database for more details", message);
    }

    @KafkaListener(topics = "payment-topic-update", groupId = "admin-group")
    public void consumeInventoryPaymentupdate(String message) throws JsonProcessingException {
        log.info("Received Message for Bus Route update :{}", message);
        InventoryUpdateRequest busInventoryRequest =
                objectMapper.readValue(message, InventoryUpdateRequest.class);
        BusInventory busInventory = busInventoryService.editAvailableSeatsByBusRouteNumber(busInventoryRequest);
        if (Objects.isNull(busInventory)) {
            log.info("[Error] Message unable to process");
        }
        log.info("The message received: {} has been processed sucessfully.", message);
    }
}
