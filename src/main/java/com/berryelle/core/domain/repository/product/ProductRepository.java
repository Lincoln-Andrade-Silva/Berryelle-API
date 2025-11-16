package com.berryelle.core.domain.repository.product;

import com.berryelle.core.domain.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.name = :name and p.id != :id")
    Optional<Product> findByNameAndIdNotEquals(String name, Long id);

    Optional<Product> findByName(String name);
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}