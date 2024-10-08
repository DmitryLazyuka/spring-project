package org.example.springproject.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlaceOrderRequestDto {
    @NotBlank
    private String shippingAddress;
}
