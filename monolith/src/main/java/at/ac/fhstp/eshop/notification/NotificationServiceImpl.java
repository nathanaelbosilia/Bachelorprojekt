package at.ac.fhstp.eshop.notification;

import at.ac.fhstp.eshop.ordering.CustomerDto;
import at.ac.fhstp.eshop.ordering.OrderDto;
import at.ac.fhstp.eshop.shipping.ShipmentDto;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendOrderConfirmationNotification(OrderDto orderDto) {
        final String emailBody = "Dear " + orderDto.customerDto().firstName() + " " + orderDto.customerDto().lastName() + "," +
                "\n\nThank you for your order with ID " + orderDto.id() + "." +
                "\nWe will process it as soon as possible.\n\n" +
                "Best regards,\nYour eShop Team";
        System.out.println("E-Mail sent to " + orderDto.customerDto().email() + ":\n" + emailBody);
    }

    @Override
    public void sendShipmentNotification(ShipmentDto shipmentDto, CustomerDto customerDto) {
        final String emailBody = "Dear " + customerDto.firstName() + " " + customerDto.lastName() + "," +
                "\n\nYour order with ID " + shipmentDto.orderId() + " has been announced for shipment with " + shipmentDto.courierName() + "." +
                "\nYou can track your shipment here: " + shipmentDto.trackingLink() + "\n\n" +
                "Best regards,\nYour eShop Team";
        System.out.println("E-Mail sent to " + customerDto.email() + ":\n" + emailBody);
    }
}
