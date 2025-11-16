package com.berryelle.controller;

import com.berryelle.core.domain.common.DomainReturnCode;
import com.berryelle.core.domain.dto.checkout.CheckoutRequest;
import com.berryelle.core.domain.dto.product.ProductRequest;
import com.berryelle.core.domain.dto.product.ProductResponse;
import com.berryelle.core.service.product.IProductService;
import com.berryelle.utils.exception.ApplicationBusinessException;
import com.berryelle.utils.request.DataRequest;
import com.berryelle.utils.response.DataListResponse;
import com.berryelle.utils.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
@CrossOrigin(origins = "${api.access.control.allow.origin}")
@Tag(name = "Product Controller", description = "Endpoints for managing products")
public class ProductController {

    private final IProductService productService;

    @Operation(
            summary = "List Products (Paginated)",
            description = "Returns a paginated list of all products registered in the Berryelle system"
    )
    @GetMapping(
            value = "/list",
            produces = "application/json"
    )
    public DataListResponse<ProductResponse> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestHeader(name = "Timezone") String timezone
    ) {
        DataListResponse<ProductResponse> response = new DataListResponse<>();
        Page<ProductResponse> pageResult = productService.list(page, pageSize, sortBy, direction, search, timezone);

        response.setData(pageResult.getContent());
        response.setTotalPages(pageResult.getTotalPages());
        response.setTotalElements(pageResult.getTotalElements());
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());

        return response;
    }

    @Operation(
            summary = "Create Product",
            description = "Registers a new product in the Berryelle system"
    )
    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = "application/json"
    )
    public DataResponse<ProductResponse> create(
            @ModelAttribute ProductRequest bodyRequest,
            @RequestHeader(name = "Timezone") String timezone
    ) throws ApplicationBusinessException, IOException {
        DataResponse<ProductResponse> response = new DataResponse<>();
        DataRequest<ProductRequest> request = new DataRequest<>(bodyRequest);

        response.setData(productService.create(request, timezone));
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());
        return response;
    }

    @Operation(
            summary = "Edit Product",
            description = "Edit product in the Berryelle system"
    )
    @PutMapping(
            value = "/edit/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = "application/json"
    )
    public DataResponse<ProductResponse> edit(
            @PathVariable(value = "id") Long id,
            @ModelAttribute ProductRequest bodyRequest,
            @RequestHeader(name = "Timezone") String timezone
    ) throws ApplicationBusinessException, IOException {
        DataResponse<ProductResponse> response = new DataResponse<>();
        DataRequest<ProductRequest> request = new DataRequest<>(bodyRequest);

        response.setData(productService.edit(id, request, timezone));
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());
        return response;
    }

    @Operation(
            summary = "Delete Product",
            description = "Delete an existing product in the Berryelle system"
    )
    @DeleteMapping(
            value = "/delete/{id}"
    )
    public DataResponse<ProductResponse> delete(
            @PathVariable(value = "id") Long id
    ) {
        DataResponse<ProductResponse> response = new DataResponse<>();
        productService.delete(id);
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());
        return response;
    }

    @Operation(
            summary = "Process Checkout",
            description = "Process checkout and update product quantities"
    )
    @PostMapping(
            value = "/checkout",
            consumes = "application/json",
            produces = "application/json"
    )
    public DataResponse<String> checkout(
            @RequestBody List<CheckoutRequest> request,
            @RequestHeader(name = "Timezone") String timezone
    ) throws ApplicationBusinessException {
        DataResponse<String> response = new DataResponse<>();
        productService.checkout(request, timezone);
        response.setData("Checkout processed successfully");
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());
        return response;
    }
}
