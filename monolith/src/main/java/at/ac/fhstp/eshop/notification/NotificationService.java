package at.ac.fhstp.eshop.notification;

import at.ac.fhstp.eshop.ordering.OrderDto;
import at.ac.fhstp.eshop.shipping.ShipmentDto;

public interface NotificationService {

    void sendOrderConfirmationNotification(OrderDto orderDto, ShipmentDto shipmentDto);
}
