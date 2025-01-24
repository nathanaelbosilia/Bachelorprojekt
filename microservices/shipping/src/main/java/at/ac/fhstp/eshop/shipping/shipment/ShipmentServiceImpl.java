package at.ac.fhstp.eshop.shipping.shipment;

import at.ac.fhstp.eshop.shipping.configuration.KafkaTopicNames;
import at.ac.fhstp.eshop.shipping.courier.Courier;
import at.ac.fhstp.eshop.shipping.courier.CourierService;
import at.ac.fhstp.eshop.shipping.events.ShipmentCreatedEvent;
import at.ac.fhstp.eshop.shipping.exceptions.ResourceNotFoundException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final KafkaTemplate<String, ShipmentCreatedEvent> kafkaTemplate;
    private final KafkaTopicNames kafkaTopicNames;
    private final ShipmentRepository shipmentRepository;
    private final CourierService courierService;

    public ShipmentServiceImpl(KafkaTemplate<String, ShipmentCreatedEvent> kafkaTemplate, KafkaTopicNames kafkaTopicNames,
                               ShipmentRepository shipmentRepository, CourierService courierService) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicNames = kafkaTopicNames;
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

        ShipmentDto createdShipmentDto;
        try {
            Shipment createdShipment = shipmentRepository.save(shipment);
            createdShipmentDto = ShipmentMapper.toDto(createdShipment);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Could not create shipment"); // TODO: create custom exception
        }

        publishShipmentCreatedEvent(kafkaTopicNames.shipmentCreated(), createdShipmentDto);

        return createdShipmentDto;
    }

    public void publishShipmentCreatedEvent(String topic, ShipmentDto shipmentDto) {
        ShipmentCreatedEvent shipmentCreatedEvent =
                new ShipmentCreatedEvent(UUID.randomUUID(), OffsetDateTime.now(), shipmentDto);
        kafkaTemplate.send(topic, shipmentDto.id().toString(), shipmentCreatedEvent);
    }
}
