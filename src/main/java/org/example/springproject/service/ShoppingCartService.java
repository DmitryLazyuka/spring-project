package org.example.springproject.service;

import org.example.springproject.dto.shoppingcart.AddToCartRequestDto;
import org.example.springproject.dto.shoppingcart.ShoppingCartDto;
import org.example.springproject.dto.shoppingcart.UpdateCartItemRequestDto;
import org.example.springproject.model.User;

public interface ShoppingCartService {
    void createCart(User user);

    ShoppingCartDto getCart(Long userId);

    ShoppingCartDto addBookToCart(AddToCartRequestDto addToCartRequestDto, User user);

    ShoppingCartDto updateCart(Long userId, Long cartItemId, UpdateCartItemRequestDto requestDto);

    void deleteFromCart(Long userId, Long cartItemId);

    void clearCart(Long userId);
}
