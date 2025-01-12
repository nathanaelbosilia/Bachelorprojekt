package at.ac.fhstp.eshop.shipping;

import java.util.UUID;

public record ShipmentDto(UUID id, UUID orderId, String courierName, ShipmentState shipmentState, String trackingLink) {
}
