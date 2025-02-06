package at.ac.fhstp.eshop.notification;

import at.ac.fhstp.eshop.ordering.OrderDto;
import at.ac.fhstp.eshop.shipping.ShipmentDto;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendOrderConfirmationNotification(OrderDto orderDto, ShipmentDto shipmentDto) {
        final String emailBody = "Dear " + orderDto.customerDto().firstName() + " " + orderDto.customerDto().lastName() + "," +
                "\n\nThank you for your order with ID " + orderDto.id() + "." +
                "\nThe order has been announced for shipment with " + shipmentDto.courierName() + " " +
                "and will be processed as soon as possible." +
                "\nYou can track your shipment here: " + shipmentDto.trackingLink() + "\n\n" +
                "Best regards,\nYour eShop Team";
        System.out.println("E-Mail sent to " + orderDto.customerDto().email() + ":\n" + emailBody);
    }
}
