package at.ac.fhstp.eshop.shipping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "couriers")
public class Courier {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
}
