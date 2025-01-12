package at.ac.fhstp.eshop.ordering;

import at.ac.fhstp.eshop.common.exceptions.ArticleUnavailableException;
import at.ac.fhstp.eshop.common.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final ArticleRepository articleRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(ArticleRepository articleRepository, CustomerRepository customerRepository,
                            OrderRepository orderRepository) {
        this.articleRepository = articleRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public Optional<OrderDto> getOrderById(UUID id) {
        return orderRepository.findById(id).map(OrderMapper::toDto);
    }

    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderDto createOrderDto) {
        Article article = articleRepository.findById(createOrderDto.articleId())
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        if(createOrderDto.quantity() > article.getAvailableQuantity()) {
            throw new ArticleUnavailableException("Not enough articles available");
        }

        article.setAvailableQuantity(article.getAvailableQuantity() - createOrderDto.quantity());
        articleRepository.save(article);

        Customer customer = customerRepository.findById(createOrderDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setArticle(article);
        orderPosition.setQuantity(createOrderDto.quantity());
        orderPosition.setPrice(article.getPrice());

        Order order = new Order();
        order.setCustomer(customer);
        order.addOrderPosition(orderPosition);

        Order createdOrder = orderRepository.save(order);
        return OrderMapper.toDto(createdOrder);
    }
}
