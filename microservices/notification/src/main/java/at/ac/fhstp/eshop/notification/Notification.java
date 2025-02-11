package at.ac.fhstp.eshop.notification;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    // TODO: store relationships to orders/shipments and customers and add additional fields

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private OffsetDateTime timestamp;
}
