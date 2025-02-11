package at.ac.fhstp.eshop.notification;

import at.ac.fhstp.eshop.notification.events.OrderCreatedEvent;
import at.ac.fhstp.eshop.notification.events.ShipmentCreatedEvent;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void sendOrderConfirmationNotification(OrderCreatedEvent orderCreatedEvent) {
        final String emailBody = "Dear " +
                orderCreatedEvent.orderDto().customerDto().firstName() + " " +
                orderCreatedEvent.orderDto().customerDto().lastName() + "," +
                "\n\nThank you for your order with ID " + orderCreatedEvent.orderDto().id() + "." +
                "\nWe will process it as soon as possible.\n\n" + "Best regards,\nYour eShop Team";
        System.out.println("E-Mail sent to " + orderCreatedEvent.orderDto().customerDto().email() + ":\n" + emailBody);

        Notification notification = new Notification();
        notificationRepository.save(notification);
    }

    @Override
    public void sendShipmentNotification(ShipmentCreatedEvent shipmentCreatedEvent) {
        final String emailBody = "Dear " +
                shipmentCreatedEvent.orderDto().customerDto().firstName() + " " +
                shipmentCreatedEvent.orderDto().customerDto().lastName() + "," +
                "\n\nYour order with ID " + shipmentCreatedEvent.orderDto().id() +
                " has been announced for shipment with " + shipmentCreatedEvent.shipmentDto().courierName() + "." +
                "\nYou can track your shipment here: " + shipmentCreatedEvent.shipmentDto().trackingLink() + "\n\n" +
                "Best regards,\nYour eShop Team";
        System.out.println("E-Mail sent to " + shipmentCreatedEvent.orderDto().customerDto().email() + ":\n" + emailBody);

        Notification notification = new Notification();
        notificationRepository.save(notification);
    }
}
