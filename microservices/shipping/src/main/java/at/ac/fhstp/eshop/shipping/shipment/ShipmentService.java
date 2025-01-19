package at.ac.fhstp.eshop.shipping.shipment;

import java.util.List;
import java.util.UUID;

public interface ShipmentService {

    List<ShipmentDto> getAllShipments();
    ShipmentDto createShipment(UUID orderId);
}
