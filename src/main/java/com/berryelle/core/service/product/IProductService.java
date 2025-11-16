package com.berryelle.core.service.product;

import com.berryelle.core.domain.dto.checkout.CheckoutRequest;
import com.berryelle.core.domain.dto.product.ProductRequest;
import com.berryelle.core.domain.dto.product.ProductResponse;
import com.berryelle.utils.exception.ApplicationBusinessException;
import com.berryelle.utils.request.DataRequest;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    Page<ProductResponse> list(int page, int size, String sortBy, String direction, String search, String timezone);

    ProductResponse create(DataRequest<ProductRequest> request, String timezone) throws ApplicationBusinessException, IOException;

    ProductResponse edit(Long id, DataRequest<ProductRequest> request, String timezone) throws ApplicationBusinessException, IOException;

    void delete(Long id);

    void checkout(List<CheckoutRequest> request, String timezone) throws ApplicationBusinessException;
}
