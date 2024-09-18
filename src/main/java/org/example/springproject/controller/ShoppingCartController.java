package org.example.springproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springproject.dto.shoppingcart.AddToCartRequestDto;
import org.example.springproject.dto.shoppingcart.ShoppingCartDto;
import org.example.springproject.dto.shoppingcart.UpdateCartItemRequestDto;
import org.example.springproject.model.User;
import org.example.springproject.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart controller", description = "Endpoint for operate with Shopping Cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Retrieve the shopping cart")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ShoppingCartDto getCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getCart(user.getId());
    }

    @Operation(summary = "Add book to the Shopping Cart")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartDto addBookToCart(Authentication authentication,
                              @RequestBody @Valid AddToCartRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addBookToCart(requestDto, user);
    }

    @Operation(summary = "Update the quantity of books in the Shopping Cart")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/items/{cartItemId}")
    public ShoppingCartDto updateCartItem(Authentication authentication,
                               @PathVariable("cartItemId") Long cartItemId,
                               @RequestBody @Valid UpdateCartItemRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateCart(user.getId(), cartItemId, requestDto);
    }

    @Operation(summary = "Remove book from the Shopping Cart")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/items/{cartItemId}")
    public void deleteCartItem(Authentication authentication,
                               @PathVariable("cartItemId") Long cartItemId) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteFromCart(user.getId(), cartItemId);
    }
}
