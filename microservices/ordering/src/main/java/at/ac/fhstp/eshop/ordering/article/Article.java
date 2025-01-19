package at.ac.fhstp.eshop.ordering.article;

import at.ac.fhstp.eshop.ordering.order.OrderPosition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String name;
    private BigDecimal price;

    private int availableQuantity;

    @OneToMany(mappedBy = "article")
    @Setter(AccessLevel.NONE)
    private Set<OrderPosition> orderPositions = new HashSet<>();
}
