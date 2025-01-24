package at.ac.fhstp.eshop.notification.events;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @KafkaListener(topics = "${spring.kafka.topics.order-created}", containerFactory = "orderCreatedEventListener")
    public void consumeOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("Received OrderCreatedEvent: " + orderCreatedEvent.id());
    }
}
