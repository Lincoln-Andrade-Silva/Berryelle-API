package com.berryelle.core.domain.dto.product;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Integer quantity;
    private Double price;
    private String createdIn;
    private String changedIn;
    private String createdBy;
    private String changedBy;

    @Override
    public String toString() {
        return "Name: " + name + ", " +
                "Description: " + description + ", " +
                "Description: " + description + ", " +
                "Quantity: " + quantity;
    }
}
