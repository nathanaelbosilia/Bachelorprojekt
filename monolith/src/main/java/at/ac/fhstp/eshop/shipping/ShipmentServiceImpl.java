package at.ac.fhstp.eshop.shipping;

import at.ac.fhstp.eshop.ordering.Order;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final EntityManager entityManager;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, EntityManager entityManager) {
        this.shipmentRepository = shipmentRepository;
        this.entityManager = entityManager;
    }

    @Override
    public ShipmentDto createShipment(CreateShipmentDto createShipmentDto) {
        Order order = entityManager.getReference(Order.class, createShipmentDto.orderId());
        Courier courier = entityManager.getReference(Courier.class, createShipmentDto.courierId());

        final String trackingLink = "https://eshop.fhstp.ac.at/tracking/" + order.getId();

        Shipment shipment = new Shipment();
        shipment.setOrder(order);
        shipment.setCourier(courier);
        shipment.setState(ShipmentState.ANNOUNCED);
        shipment.setTrackingLink(trackingLink);

        Shipment createdShipment = shipmentRepository.save(shipment);
        return ShipmentMapper.toDto(createdShipment);
    }
}
