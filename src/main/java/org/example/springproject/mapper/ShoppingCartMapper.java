package org.example.springproject.mapper;

import org.example.springproject.config.MapperConfig;
import org.example.springproject.dto.shoppingcart.CartItemResponseDto;
import org.example.springproject.dto.shoppingcart.ShoppingCartDto;
import org.example.springproject.model.Book;
import org.example.springproject.model.CartItem;
import org.example.springproject.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "cartItems")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemResponseDto toCartItemDto(CartItem cartItem);

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
