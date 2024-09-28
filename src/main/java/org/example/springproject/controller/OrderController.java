package org.example.springproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.springproject.dto.order.OrderDto;
import org.example.springproject.dto.order.OrderItemDto;
import org.example.springproject.dto.order.PlaceOrderRequestDto;
import org.example.springproject.dto.order.StatusUpdateRequestDto;
import org.example.springproject.model.User;
import org.example.springproject.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Retrieve the history of orders")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<OrderDto> getOrders(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrders(user.getId());
    }

    @Operation(summary = "Place an order")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDto placeOrder(Authentication authentication,
                               @RequestBody @Valid PlaceOrderRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(user.getId(), requestDto);
    }

    @Operation(summary = "Retrieve order details")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> getOrderItems(Authentication authentication,
                                            @PathVariable Long orderId) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItems(user.getId(), orderId);
    }

    @Operation(summary = "Retrieve a specific item from order")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items/{id}")
    public OrderItemDto getSpecificItem(Authentication authentication,
                                        @PathVariable Long orderId,
                                        @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();
        return orderService.getSpecificItem(user.getId(), orderId, id);
    }

    @Operation(summary = "Update Order Status")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    OrderDto updateOrderStatus(@RequestBody StatusUpdateRequestDto request,
                               @PathVariable Long orderId) {
        return orderService.updateOrderStatus(orderId, request);
    }
}
