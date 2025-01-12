package at.ac.fhstp.eshop.shipping;

import java.util.UUID;

public record CreateShipmentDto(UUID orderId, UUID courierId) {
}
