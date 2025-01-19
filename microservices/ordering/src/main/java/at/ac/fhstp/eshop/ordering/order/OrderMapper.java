package at.ac.fhstp.eshop.ordering.order;

import at.ac.fhstp.eshop.ordering.customer.CustomerMapper;

public class OrderMapper {

    public static OrderDto toDto(Order order) {
        return new OrderDto(
                order.getId(),
                CustomerMapper.toDto(order.getCustomer()),
                order.getOrderDate()
        );
    }
}
