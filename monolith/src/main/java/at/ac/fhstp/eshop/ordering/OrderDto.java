package at.ac.fhstp.eshop.ordering;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderDto(UUID id, CustomerDto customerDto, OffsetDateTime orderDate) {
}
