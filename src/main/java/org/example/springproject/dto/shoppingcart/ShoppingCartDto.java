package org.example.springproject.dto.shoppingcart;

import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private List<CartItemResponseDto> cartItems;
}
