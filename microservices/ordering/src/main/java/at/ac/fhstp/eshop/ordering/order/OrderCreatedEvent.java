package at.ac.fhstp.eshop.ordering.order;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderCreatedEvent(UUID id, OffsetDateTime eventDate, OrderDto orderDto) {
}
