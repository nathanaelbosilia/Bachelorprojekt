package at.ac.fhstp.eshop.notification.events;

import at.ac.fhstp.eshop.notification.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    private final NotificationService notificationService;

    public OrderEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${spring.kafka.topics.order-created}", containerFactory = "orderCreatedEventListener")
    public void consumeOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("Received OrderCreatedEvent: " + orderCreatedEvent.id());
        notificationService.sendOrderConfirmationNotification(orderCreatedEvent);
    }
}