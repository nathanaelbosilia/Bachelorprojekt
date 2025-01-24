package at.ac.fhstp.eshop.notification.events;

import java.time.OffsetDateTime;
import java.util.UUID;

// TODO: rework this
public record ShipmentCreatedEvent(UUID id, OffsetDateTime eventDate, ShipmentDto shipmentDto, OrderDto orderDto) {
}