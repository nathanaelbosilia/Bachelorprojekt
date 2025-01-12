package at.ac.fhstp.eshop.shipping;

public class ShipmentMapper {

    public static ShipmentDto toDto(Shipment shipment) {
        return new ShipmentDto(
                shipment.getId(),
                shipment.getOrder().getId(),
                shipment.getCourier().getName(),
                shipment.getState(),
                shipment.getTrackingLink()
        );
    }
}
