package at.ac.fhstp.eshop.shipping.events;

import at.ac.fhstp.eshop.shipping.shipment.ShipmentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    private final ShipmentService shipmentService;

    public OrderEventConsumer(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @KafkaListener(topics = "${spring.kafka.topics.order-created}", containerFactory = "orderCreatedEventListener")
    public void consumeOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("Received OrderCreatedEvent: " + orderCreatedEvent.id());
        shipmentService.createShipment(orderCreatedEvent.orderDto().id());
    }
}
