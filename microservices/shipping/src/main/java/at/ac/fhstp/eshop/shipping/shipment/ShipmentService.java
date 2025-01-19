package at.ac.fhstp.eshop.shipping.shipment;

import at.ac.fhstp.eshop.shipping.events.OrderDto;

import java.util.List;

public interface ShipmentService {

    List<ShipmentDto> getAllShipments();
    ShipmentDto createShipment(OrderDto orderDto);
}
