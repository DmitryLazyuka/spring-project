package org.example.springproject.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookSearchParametersDto {
    private String[] titles;
    private String[] authors;
    private BigDecimal fromPrice;
    private BigDecimal toPrice;
}
