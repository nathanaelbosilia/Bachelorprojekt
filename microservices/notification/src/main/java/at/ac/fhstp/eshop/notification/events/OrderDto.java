package at.ac.fhstp.eshop.notification.events;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderDto(UUID id, CustomerDto customerDto, OffsetDateTime orderDate) {
}
