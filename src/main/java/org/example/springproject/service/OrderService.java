package org.example.springproject.service;

import java.util.List;
import org.example.springproject.dto.order.OrderDto;
import org.example.springproject.dto.order.OrderItemDto;
import org.example.springproject.dto.order.PlaceOrderRequestDto;
import org.example.springproject.dto.order.StatusUpdateRequestDto;

public interface OrderService {
    List<OrderDto> getOrders(Long userId);

    OrderDto placeOrder(Long userId, PlaceOrderRequestDto requestDto);

    List<OrderItemDto> getOrderItems(Long userId, Long orderId);

    OrderItemDto getSpecificItem(Long userId, Long orderId, Long itemId);

    OrderDto updateOrderStatus(Long orderId, StatusUpdateRequestDto request);
}
