package at.ac.fhstp.eshop.ordering;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private OffsetDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderPosition> orderPositions = new HashSet<>();

    public void addOrderPosition(OrderPosition orderPosition) {
        orderPosition.setOrder(this);
        orderPositions.add(orderPosition);
    }

    @PrePersist
    private void prePersist() {
        this.orderDate = OffsetDateTime.now();
    }
}
