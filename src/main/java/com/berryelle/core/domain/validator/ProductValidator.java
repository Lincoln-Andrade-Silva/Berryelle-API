package com.berryelle.core.domain.validator;

import com.berryelle.core.domain.common.DomainReturnCode;
import com.berryelle.core.domain.dto.product.ProductRequest;
import com.berryelle.core.domain.model.product.Product;
import com.berryelle.utils.exception.ApplicationBusinessException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@NoArgsConstructor
public class ProductValidator {

    public static void validateIfProductExists(Optional<Product> product) throws ApplicationBusinessException {
        if (product.isPresent()) {
            throw new ApplicationBusinessException(
                    HttpServletResponse.SC_BAD_REQUEST,
                    DomainReturnCode.PRODUCT_EXISTS.toString(),
                    DomainReturnCode.PRODUCT_EXISTS.getDesc()
            );
        }
    }

    public static void validateIfSameNameExists(Integer same) throws ApplicationBusinessException {
        if (same > 0) {
            throw new ApplicationBusinessException(
                    HttpServletResponse.SC_BAD_REQUEST,
                    DomainReturnCode.PRODUCT_NAME_EXISTS.toString(),
                    DomainReturnCode.PRODUCT_NAME_EXISTS.getDesc()
            );
        }
    }

    public static void validateIfInvalidQuantity(ProductRequest product) throws ApplicationBusinessException {
        if (product.getQuantity() == null || product.getQuantity() < 0) {
            throw new ApplicationBusinessException(
                    HttpServletResponse.SC_BAD_REQUEST,
                    DomainReturnCode.INVALID_QUANTITY.toString(),
                    DomainReturnCode.INVALID_QUANTITY.getDesc()
            );
        }
    }

    public static void validateIfInvalidPrice(ProductRequest product) throws ApplicationBusinessException {
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new ApplicationBusinessException(
                    HttpServletResponse.SC_BAD_REQUEST,
                    DomainReturnCode.INVALID_PRICE.toString(),
                    DomainReturnCode.INVALID_PRICE.getDesc()
            );
        }
    }

    public static void validateToCreate(ProductRequest dto, Optional<Product> existingProduct) throws ApplicationBusinessException {
        validateIfProductExists(existingProduct);
        validateIfInvalidQuantity(dto);
        validateIfInvalidPrice(dto);
    }

    public static void validateToEdit(ProductRequest dto, Optional<Product> sameNameProduct) throws ApplicationBusinessException {
        if (sameNameProduct.isPresent()) {
            throw new ApplicationBusinessException(
                    HttpServletResponse.SC_BAD_REQUEST,
                    DomainReturnCode.PRODUCT_NAME_EXISTS.toString(),
                    DomainReturnCode.PRODUCT_NAME_EXISTS.getDesc()
            );
        }
        validateIfInvalidQuantity(dto);
        validateIfInvalidPrice(dto);
    }
}