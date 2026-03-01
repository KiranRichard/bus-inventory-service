package com.online.bus.ticket.reservation.inventory.validator;

import com.online.bus.ticket.reservation.inventory.exception.RequiredFieldsMissingException;
import com.online.bus.ticket.reservation.inventory.request.BusInventoryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class BusInventoryValidator {

    public void validateBusInventory(BusInventoryRequest busInventoryRequest) {
        if (Objects.isNull(busInventoryRequest)) {
            log.info("[Error]: Invalid - bus inventory request is null");
            throw new RequiredFieldsMissingException("Invalid - bus inventory request is null");
        }

        if(busInventoryRequest.getBusRouteNumber()<=0) {
            log.info("[Error]: Invalid bus route number is null in bus inventory passenger request");
            throw new RequiredFieldsMissingException("Invalid bus route number is null in bus inventory passenger request");
        }

        if(busInventoryRequest.getTotalSeats()<=0) {
            log.info("[Error]: Invalid total seats is null in bus inventory passenger request");
            throw new RequiredFieldsMissingException("Invalid total seats is null in bus inventory passenger request");
        }

        if(busInventoryRequest.getAvailableSeats()<=0) {
            log.info("[Error]: Invalid available seats is null in bus inventory passenger request");
            throw new RequiredFieldsMissingException("Invalid available seats is null in bus inventory passenger request");
        }
    }

    public void validateBusInventoryId(long busInventoryId) {
        if(busInventoryId<=0) {
            log.info("[Error]: Invalid - bus inventory Id is null");
            throw new RequiredFieldsMissingException("Invalid - bus inventory Id is null");
        }
    }

    public void validateBusRouteNumber(long busRouteNumber) {
        if(busRouteNumber<=0) {
            log.info("[Error]: Invalid - bus route number is null");
            throw new RequiredFieldsMissingException("Invalid - bus route number is null");
        }
    }
}
