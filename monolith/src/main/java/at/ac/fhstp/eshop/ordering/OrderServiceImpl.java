package at.ac.fhstp.eshop.ordering;

import at.ac.fhstp.eshop.common.exceptions.ArticleUnavailableException;
import at.ac.fhstp.eshop.common.exceptions.ResourceNotFoundException;
import at.ac.fhstp.eshop.notification.NotificationService;
import at.ac.fhstp.eshop.shipping.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final ArticleRepository articleRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    private final ShipmentService shipmentService;
    private final NotificationService notificationService;

    public OrderServiceImpl(ArticleRepository articleRepository, CustomerRepository customerRepository,
                            OrderRepository orderRepository, CourierRepository courierRepository,
                            ShipmentService shipmentService, NotificationService notificationService) {
        this.articleRepository = articleRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.courierRepository = courierRepository;
        this.shipmentService = shipmentService;
        this.notificationService = notificationService;
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
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        // check if enough articles are available
        if (createOrderDto.quantity() > article.getAvailableQuantity()) {
            throw new ArticleUnavailableException("Not enough articles available");
        }

        // get customer
        Customer customer = customerRepository.findById(createOrderDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

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

        // send order confirmation notification
        notificationService.sendOrderConfirmationNotification(createdOrderDto);

        // get courier
        final String courierName = "DHL";
        Courier courier = courierRepository.findByName(courierName)
                .orElseThrow(() -> new ResourceNotFoundException("Courier with name " + courierName + " not found"));

        // create shipment
        CreateShipmentDto createShipmentDto = new CreateShipmentDto(createdOrder, courier);
        ShipmentDto shipmentDto = shipmentService.createShipment(createShipmentDto);

        // send shipment notification
        notificationService.sendShipmentNotification(shipmentDto, createdOrderDto.customerDto());

        // return created order
        return createdOrderDto;
    }
}
