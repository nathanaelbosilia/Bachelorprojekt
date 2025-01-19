package at.ac.fhstp.eshop.shipping.shipment;

import at.ac.fhstp.eshop.shipping.courier.Courier;
import at.ac.fhstp.eshop.shipping.courier.CourierService;
import at.ac.fhstp.eshop.shipping.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final CourierService courierService;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, CourierService courierService) {
        this.shipmentRepository = shipmentRepository;
        this.courierService = courierService;
    }

    @Override
    public List<ShipmentDto> getAllShipments() {
        return shipmentRepository.findAll().stream()
                .map(ShipmentMapper::toDto)
                .toList();
    }

    @Override
    public ShipmentDto createShipment(UUID orderId) {
        final String trackingLink = "https://eshop.fhstp.ac.at/tracking/" + orderId;

        Courier courier = courierService.getCourierByName("DHL");

        Shipment shipment = new Shipment();
        shipment.setOrderId(orderId);
        shipment.setCourier(courier);
        shipment.setState(ShipmentState.ANNOUNCED);
        shipment.setTrackingLink(trackingLink);

        Shipment createdShipment;
        try {
            createdShipment = shipmentRepository.save(shipment);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Could not create shipment");
        }
        return ShipmentMapper.toDto(createdShipment);
    }
}
