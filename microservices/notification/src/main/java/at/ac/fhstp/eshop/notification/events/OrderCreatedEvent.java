package at.ac.fhstp.eshop.notification.events;

import java.time.OffsetDateTime;
import java.util.UUID;

// TODO: check if all of these properties are needed as we are currently only using the order id
public record OrderCreatedEvent(UUID id, OffsetDateTime eventDate, OrderDto orderDto) {
}

record OrderDto(UUID id, CustomerDto customerDto, OffsetDateTime orderDate) {
}

record CustomerDto(UUID id, String firstName, String lastName, String email) {
}