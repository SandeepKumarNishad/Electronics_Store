package com.Electronic.Store.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemToCartRequest {
    private  String productId;
    private  int quantity;
}
