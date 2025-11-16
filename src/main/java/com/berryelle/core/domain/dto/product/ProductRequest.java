package com.berryelle.core.domain.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    @Schema(type = "string", format = "binary")
    private MultipartFile image;
    private Integer quantity;
    private Double price;

    @Override
    public String toString() {
        return "Name: " + name + ", " +
               "Description: " + description + ", " +
               "Description: " + description + ", " +
               "Quantity: " + quantity;
    }
}
