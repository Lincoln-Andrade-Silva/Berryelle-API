package com.berryelle.core.service.product;

import com.berryelle.core.domain.dto.checkout.CheckoutRequest;
import com.berryelle.core.domain.dto.product.ProductRequest;
import com.berryelle.core.domain.dto.product.ProductResponse;
import com.berryelle.core.domain.model.product.Product;
import com.berryelle.core.domain.repository.product.ProductRepository;
import com.berryelle.core.domain.repository.product.ProductSpecification;
import com.berryelle.core.domain.validator.ProductValidator;
import com.berryelle.core.mapper.ProductMapper;
import com.berryelle.utils.exception.ApplicationBusinessException;
import com.berryelle.utils.request.DataRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository repository;

    @Override
    public Page<ProductResponse> list(int page, int size, String sortBy, String direction, String search, String timezone) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Product> spec = (search != null && !search.trim().isEmpty()) ? ProductSpecification.containsTextInAttributes(search) : null;
        return (spec != null ? repository.findAll(spec, pageable) : repository.findAll(pageable))
                .map(product -> {
                    try {
                        return ProductMapper.toDTO(product, timezone);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public ProductResponse create(DataRequest<ProductRequest> request, String timezone) throws ApplicationBusinessException, IOException {
        ProductRequest dto = request.getData();

        Optional<Product> existingProduct = repository.findByName(dto.getName());
        ProductValidator.validateToCreate(dto, existingProduct);

        Product product = ProductMapper.toEntity(dto);
        Product savedProduct = repository.save(product);

        return ProductMapper.toDTO(savedProduct, timezone);
    }

    @Override
    public ProductResponse edit(Long id, DataRequest<ProductRequest> request, String timezone) throws ApplicationBusinessException, IOException {
        ProductRequest dto = request.getData();

        Product existing = repository.getReferenceById(id);

        Optional<Product> sameNameProduct = repository.findByNameAndIdNotEquals(dto.getName(), id);
        ProductValidator.validateToEdit(dto, sameNameProduct);

        ProductMapper.updateEntity(existing, dto);

        Product saved = repository.save(existing);
        return ProductMapper.toDTO(saved, timezone);
    }

    @Override
    public void delete(Long id) {
        Product product = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.delete(product);
    }

    @Override
    public void checkout(List<CheckoutRequest> checkoutRequest, String timezone) throws ApplicationBusinessException {

        for (CheckoutRequest item : checkoutRequest) {
            Product product = repository.findById(item.getProductId())
                    .orElseThrow(EntityNotFoundException::new);

            if (product.getQuantity() < item.getQuantity()) {
                throw new ApplicationBusinessException("Insufficient stock for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - item.getQuantity());
            repository.save(product);
        }
    }
}