package at.ac.fhstp.eshop.shipping.events;

import java.util.UUID;

public record CustomerDto(UUID id, String firstName, String lastName, String email) {
}
