package at.ac.fhstp.eshop.ordering.customer;

import java.util.UUID;

public record CustomerDto(UUID id, String firstName, String lastName, String email) {
}
