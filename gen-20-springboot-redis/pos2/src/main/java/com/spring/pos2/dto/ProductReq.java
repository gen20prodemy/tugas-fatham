package com.spring.pos2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReq {
    private Long productId;
    private String productName;
    private Long categoryId;

}
