package at.ac.fhstp.eshop.ordering;

import java.util.UUID;

public record CustomerDto(UUID id, String firstName, String lastName, String email) {
}
