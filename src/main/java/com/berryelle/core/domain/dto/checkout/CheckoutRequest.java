package com.berryelle.core.domain.dto.checkout;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {
    private Long productId;
    private Integer quantity;
}
