package at.ac.fhstp.eshop.ordering.order;

import at.ac.fhstp.eshop.ordering.article.Article;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "order_positions")
public class OrderPosition {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    private int quantity;
    private BigDecimal price;
}
