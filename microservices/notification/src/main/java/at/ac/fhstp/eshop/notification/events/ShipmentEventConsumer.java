package at.ac.fhstp.eshop.notification.events;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ShipmentEventConsumer {

    @KafkaListener(topics = "${spring.kafka.topics.shipment-created}", containerFactory = "shipmentCreatedEventListener")
    public void consumeShipmentCreatedEvent(ShipmentCreatedEvent shipmentCreatedEvent) {
        System.out.println("Received ShipmentCreatedEvent: " + shipmentCreatedEvent.id());
    }
}
