package at.ac.fhstp.eshop.shipping.events;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderCreatedEvent(UUID id, OffsetDateTime eventDate, OrderDto orderDto) {
}

record OrderDto(UUID id, CustomerDto customerDto, OffsetDateTime orderDate) {
}

record CustomerDto(UUID id, String firstName, String lastName, String email) {
}