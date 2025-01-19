package at.ac.fhstp.eshop.shipping.shipment;

public class ShipmentMapper {

    public static ShipmentDto toDto(Shipment shipment) {
        return new ShipmentDto(
                shipment.getId(),
                shipment.getOrderId(),
                shipment.getCourier().getName(),
                shipment.getState(),
                shipment.getTrackingLink()
        );
    }
}
