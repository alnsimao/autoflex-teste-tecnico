package com.aln.autoflex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_compositions")
public class ProductComposition {

    @EmbeddedId
    private ProductCompositionKey id = new ProductCompositionKey();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("rawMaterialId")
    @JoinColumn(name = "raw_material_id")
    private RawMaterial rawMaterial;


    @Column(name = "quantity_needed", nullable = false, precision = 15, scale = 4)
    private BigDecimal quantityNeeded;
}
