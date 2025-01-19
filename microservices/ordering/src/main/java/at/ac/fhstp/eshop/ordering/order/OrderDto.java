package at.ac.fhstp.eshop.ordering.order;

import at.ac.fhstp.eshop.ordering.customer.CustomerDto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderDto(UUID id, CustomerDto customerDto, OffsetDateTime orderDate) {
}
