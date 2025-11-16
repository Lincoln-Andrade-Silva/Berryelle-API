package com.berryelle.core.mapper;

import com.berryelle.core.domain.dto.product.ProductRequest;
import com.berryelle.core.domain.dto.product.ProductResponse;
import com.berryelle.core.domain.model.product.Product;
import com.berryelle.utils.date.DateUtils;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Base64;

@NoArgsConstructor
public class ProductMapper {

    public static Product toEntity(ProductRequest request) throws IOException {
        byte[] imageBytes = request.getImage() != null ? request.getImage().getBytes() : null;
        return Product.builder()
                .name(request.getName())
                .image(imageBytes)
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .description(request.getDescription())
                .build();
    }

    public static ProductResponse toDTO(Product product, String timezone) throws IOException {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .image(product.getImage() != null ? Base64.getEncoder().encodeToString(product.getImage()) : "")
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .createdBy(product.getCreatedBy().toString())
                .createdIn(DateUtils.formatLocalDateTimeToString(product.getCreatedIn(), timezone))
                .changedIn(product.getChangedIn() != null ? DateUtils.formatLocalDateTimeToString(product.getChangedIn(),
                        timezone) : null)
                .changedBy(product.getChangedBy() != null ? product.getChangedBy().toString() : null)
                .build();
    }

    public static void updateEntity(Product existing, ProductRequest dto) throws IOException {
        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());
        existing.setImage(dto.getImage().getBytes());
        existing.setQuantity(dto.getQuantity());
        existing.setDescription(dto.getDescription());
    }
}
