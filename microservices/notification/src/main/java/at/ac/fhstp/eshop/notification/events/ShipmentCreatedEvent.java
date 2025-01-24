package at.ac.fhstp.eshop.notification.events;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

// TODO: rework this
public record ShipmentCreatedEvent(UUID id, OffsetDateTime eventDate, ShipmentDto shipmentDto) {
}

record ShipmentDto(UUID id, UUID orderId, String courierName, ShipmentState shipmentState, String trackingLink) {
}

@Getter
enum ShipmentState {

    ANNOUNCED("The shipment has been electronically announced."),
    IN_TRANSIT("The shipment has been processed and prepared for transit to the recipient's region."),
    ARRIVED("The shipment has arrived in the recipient's region and will be transported to the delivery base."),
    IN_DELIVERY("The shipment has been loaded into the delivery vehicle."),
    DELIVERED("The shipment has been successfully delivered.");

    private final String description;

    ShipmentState(String description) {
        this.description = description;
    }
}