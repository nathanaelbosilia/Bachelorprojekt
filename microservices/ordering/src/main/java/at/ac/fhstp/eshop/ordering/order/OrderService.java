package at.ac.fhstp.eshop.ordering.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    List<OrderDto> getAllOrders();
    Optional<OrderDto> getOrderById(UUID id);
    OrderDto createOrder(CreateOrderDto createOrderDto);
}
