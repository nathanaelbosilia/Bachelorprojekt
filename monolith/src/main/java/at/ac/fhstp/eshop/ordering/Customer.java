package at.ac.fhstp.eshop.ordering;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String zipCode;
    private String country;
}
