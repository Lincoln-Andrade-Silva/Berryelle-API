package com.berryelle.core.domain.model.product;

import com.berryelle.core.domain.model.audit.AuditFields;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_product", indexes = {@Index(name = "index_tb_product", columnList = "id, name")})
public class Product extends AuditFields {

    @Serial
    private static final long serialVersionUID = 916589224142488389L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;
}
