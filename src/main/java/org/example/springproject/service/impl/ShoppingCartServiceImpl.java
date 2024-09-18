package org.example.springproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springproject.dto.shoppingcart.AddToCartRequestDto;
import org.example.springproject.dto.shoppingcart.ShoppingCartDto;
import org.example.springproject.dto.shoppingcart.UpdateCartItemRequestDto;
import org.example.springproject.mapper.ShoppingCartMapper;
import org.example.springproject.model.Book;
import org.example.springproject.model.CartItem;
import org.example.springproject.model.ShoppingCart;
import org.example.springproject.model.User;
import org.example.springproject.repository.book.BookRepository;
import org.example.springproject.repository.cart.CartItemRepository;
import org.example.springproject.repository.cart.ShoppingCartRepository;
import org.example.springproject.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public void createCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto getCart(Long userId) {
        return shoppingCartMapper.toDto(shoppingCartRepository.findByUserId(userId));
    }

    @Transactional
    @Override
    public ShoppingCartDto addBookToCart(AddToCartRequestDto requestDto, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(EntityNotFoundException::new);
        shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(requestDto.getBookId()))
                .findFirst()
                .ifPresentOrElse(item -> item.setQuantity(
                        item.getQuantity() + requestDto.getQuantity()),
                        () -> addCartItemToCart(requestDto, book, shoppingCart));
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    private void addCartItemToCart(AddToCartRequestDto requestDto,
                                   Book book,
                                   ShoppingCart shoppingCart) {
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItem.setShoppingCart(shoppingCart);
        shoppingCart.getCartItems().add(cartItem);
    }

    @Override
    public ShoppingCartDto updateCart(Long userId,
                                      Long cartItemId,
                                      UpdateCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(
                cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Cart item with ID " + cartItemId));
        cartItem.setQuantity(requestDto.getQuantity());
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public void deleteFromCart(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(
                cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Cart item with ID " + cartItemId));
        shoppingCart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }
}
