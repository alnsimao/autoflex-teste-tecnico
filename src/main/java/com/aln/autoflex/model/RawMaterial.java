package com.aln.autoflex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "raw_materials")
public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "stock_quantity",nullable = false, precision = 15, scale = 4)
    private BigDecimal stockQuantity = BigDecimal.ZERO;

    @Column(nullable = false, updatable = true)
    @CreationTimestamp
    private LocalDateTime createdAt;

}
