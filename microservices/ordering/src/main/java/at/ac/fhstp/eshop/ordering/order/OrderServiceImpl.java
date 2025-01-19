package at.ac.fhstp.eshop.ordering.order;

import at.ac.fhstp.eshop.ordering.article.Article;
import at.ac.fhstp.eshop.ordering.article.ArticleRepository;
import at.ac.fhstp.eshop.ordering.configuration.KafkaTopicNames;
import at.ac.fhstp.eshop.ordering.customer.Customer;
import at.ac.fhstp.eshop.ordering.customer.CustomerRepository;
import at.ac.fhstp.eshop.ordering.exceptions.ArticleUnavailableException;
import at.ac.fhstp.eshop.ordering.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final KafkaTopicNames kafkaTopicNames;

    private final ArticleRepository articleRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate, KafkaTopicNames kafkaTopicNames,
                            ArticleRepository articleRepository, CustomerRepository customerRepository,
                            OrderRepository orderRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicNames = kafkaTopicNames;
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
        // get article
        Article article = articleRepository.findById(createOrderDto.articleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Article with ID " + createOrderDto.articleId() + " not found"));

        // check if enough articles are available
        if (createOrderDto.quantity() > article.getAvailableQuantity()) {
            throw new ArticleUnavailableException("Not enough articles available");
        }

        // get customer
        Customer customer = customerRepository.findById(createOrderDto.customerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer with ID " + createOrderDto.customerId() + " not found"));

        // update available quantity
        article.setAvailableQuantity(article.getAvailableQuantity() - createOrderDto.quantity());
        articleRepository.save(article);

        // create order position
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setArticle(article);
        orderPosition.setQuantity(createOrderDto.quantity());
        orderPosition.setPrice(article.getPrice());

        // create order
        Order order = new Order();
        order.setCustomer(customer);
        order.addOrderPosition(orderPosition);

        // save order and map to dto
        Order createdOrder = orderRepository.save(order);
        OrderDto createdOrderDto = OrderMapper.toDto(createdOrder);

        // public event only after successful transaction commit
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                publishOrderCreatedEvent(kafkaTopicNames.orderCreated(), createdOrderDto);
            }
        });

        // return created order
        return createdOrderDto;
    }

    public void publishOrderCreatedEvent(String topic, OrderDto orderDto) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(UUID.randomUUID(), OffsetDateTime.now(), orderDto);
        kafkaTemplate.send(topic, orderDto.id().toString(), orderCreatedEvent);
    }
}
