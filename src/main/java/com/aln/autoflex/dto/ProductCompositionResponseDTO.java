package com.aln.autoflex.dto;

import java.math.BigDecimal;

public record ProductCompositionResponseDTO(
        Long id,
        Long productId,
        String productName,
        Long rawMaterialId,
        String rawMaterialName,
        BigDecimal quantityNeeded
) {
}
