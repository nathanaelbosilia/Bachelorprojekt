package at.ac.fhstp.eshop.shipping.shipment;

import java.util.UUID;

public record ShipmentDto(UUID id, UUID orderId, String courierName, ShipmentState shipmentState, String trackingLink) {
}
