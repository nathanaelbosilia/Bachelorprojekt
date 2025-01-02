package at.ac.fhstp.eshop.ordering;

import java.util.UUID;

// TODO: add validation
public record CreateOrderDto(UUID articleId, UUID customerId, int quantity) {
}
