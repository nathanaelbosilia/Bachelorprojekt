package at.ac.fhstp.eshop.shipping;

import at.ac.fhstp.eshop.common.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public ShipmentDto createShipment(CreateShipmentDto createShipmentDto) {
        final String trackingLink = "https://eshop.fhstp.ac.at/tracking/" + createShipmentDto.order().getId();

        Shipment shipment = new Shipment();
        shipment.setOrder(createShipmentDto.order());
        shipment.setCourier(createShipmentDto.courier());
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
