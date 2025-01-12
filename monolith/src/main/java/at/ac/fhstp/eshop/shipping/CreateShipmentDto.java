package at.ac.fhstp.eshop.shipping;

import at.ac.fhstp.eshop.ordering.Order;

public record CreateShipmentDto(Order order, Courier courier) {
}
