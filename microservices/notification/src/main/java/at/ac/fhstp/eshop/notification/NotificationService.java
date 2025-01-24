package at.ac.fhstp.eshop.notification;

import at.ac.fhstp.eshop.notification.events.OrderCreatedEvent;
import at.ac.fhstp.eshop.notification.events.ShipmentCreatedEvent;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    void sendOrderConfirmationNotification(OrderCreatedEvent orderCreatedEvent);

    void sendShipmentNotification(ShipmentCreatedEvent shipmentCreatedEvent);
}
