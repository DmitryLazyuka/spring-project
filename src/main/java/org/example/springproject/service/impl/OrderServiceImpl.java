package org.example.springproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.springproject.dto.order.OrderDto;
import org.example.springproject.dto.order.OrderItemDto;
import org.example.springproject.dto.order.OrderUpdateRequestDto;
import org.example.springproject.dto.order.PlaceOrderRequestDto;
import org.example.springproject.exception.OrderProcessingException;
import org.example.springproject.mapper.OrderMapper;
import org.example.springproject.model.Order;
import org.example.springproject.model.OrderItem;
import org.example.springproject.model.ShoppingCart;
import org.example.springproject.model.Status;
import org.example.springproject.repository.cart.ShoppingCartRepository;
import org.example.springproject.repository.order.OrderItemRepository;
import org.example.springproject.repository.order.OrderRepository;
import org.example.springproject.service.OrderService;
import org.example.springproject.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public List<OrderDto> getOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderDto placeOrder(Long userId, PlaceOrderRequestDto requestDto) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new OrderProcessingException("Cart is empty or not available");
        }
        Order order = createOrderFromCart(cart);
        order.setShippingAddress(requestDto.getShippingAddress());
        orderRepository.save(order);
        shoppingCartService.clearCart(userId);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Order with " + orderId
                        + " orderId is not found for current user"));
        return order.getOrderItems().stream()
                .map(orderMapper::toOrderItemDto)
                .toList();
    }

    @Override
    public OrderItemDto getSpecificItem(Long userId, Long orderId, Long itemId) {
        OrderItem item = orderItemRepository.findByIdAndOrderIdAndUserId(itemId, orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Order item with "
                        + itemId + " is not found for order " + orderId + " of current user"));
        return orderMapper.toOrderItemDto(item);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderUpdateRequestDto request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no order with id " + orderId));
        order.setStatus(request.getStatus());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private Order createOrderFromCart(ShoppingCart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        Set<OrderItem> orderItems = cart.getCartItems()
                .stream().map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(
                            cartItem.getBook().getPrice().multiply(
                                    BigDecimal.valueOf(cartItem.getQuantity())));
                    orderItem.setOrder(order);
                    return orderItem;
                }).collect(Collectors.toSet());
        order.setOrderItems(orderItems);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(sumTotalPrice(orderItems));
        order.setStatus(Status.PENDING);
        return order;
    }

    private BigDecimal sumTotalPrice(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
