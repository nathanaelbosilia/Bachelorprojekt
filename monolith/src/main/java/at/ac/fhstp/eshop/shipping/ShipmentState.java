package at.ac.fhstp.eshop.shipping;

public enum ShipmentState {

    ANNOUNCED("The shipment has been electronically announced."),
    IN_TRANSIT("The shipment has been processed and prepared for transit to the recipient's region."),
    ARRIVED("The shipment has arrived in the recipient's region and will be transported to the delivery base."),
    IN_DELIVERY("The shipment has been loaded into the delivery vehicle."),
    DELIVERED("The shipment has been successfully delivered.");

    private final String description;

    ShipmentState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
