package at.ac.fhstp.eshop.ordering;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime orderDate;

    @OneToMany(mappedBy = "order")
    private Set<OrderPosition> orderPositions;
}
