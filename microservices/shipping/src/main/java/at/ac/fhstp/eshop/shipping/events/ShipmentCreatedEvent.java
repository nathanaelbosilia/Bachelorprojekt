package at.ac.fhstp.eshop.shipping.events;

import at.ac.fhstp.eshop.shipping.shipment.ShipmentDto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ShipmentCreatedEvent(UUID id, OffsetDateTime eventDate, ShipmentDto shipmentDto, OrderDto orderDto) {
}
