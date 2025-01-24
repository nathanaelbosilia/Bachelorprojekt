package at.ac.fhstp.eshop.notification.events;

import at.ac.fhstp.eshop.notification.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ShipmentEventConsumer {

    private final NotificationService notificationService;

    public ShipmentEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${spring.kafka.topics.shipment-created}", containerFactory = "shipmentCreatedEventListener")
    public void consumeShipmentCreatedEvent(ShipmentCreatedEvent shipmentCreatedEvent) {
        System.out.println("Received ShipmentCreatedEvent: " + shipmentCreatedEvent.id());
        notificationService.sendShipmentNotification(shipmentCreatedEvent);
    }
}
