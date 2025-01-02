package at.ac.fhstp.eshop.ordering;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private BigDecimal price;
    private int availableQuantity;

    @OneToMany(mappedBy = "article")
    private Set<OrderPosition> orderPositions;
}
